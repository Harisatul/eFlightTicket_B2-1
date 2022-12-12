package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.*;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.RouteRepository;
import org.binar.eflightticket_b2.repository.ScheduleRepository;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private static final String ENTITY = "schedule";

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Schedule addSchedule(ScheduleRequest scheduleRequest) {

        Route route = routeRepository.findById(scheduleRequest.getRouteId())
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("route", "id", scheduleRequest.getRouteId());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Route found with ID : {}", route.getId());

        Schedule result = Schedule.builder()
                .departureDate(scheduleRequest.getDepartureDate())
                .arrivalDate(scheduleRequest.getArrivalDate())
                .departureTime(scheduleRequest.getDepartureTime())
                .arrivalTime(scheduleRequest.getArrivalTime())
                .route(route)
                .netPrice(scheduleRequest.getNetPrice())
                .build();

        scheduleRepository.save(result);

        log.info("Has successfully created schedule data with ID : {}", result.getId());

        return result;
    }

    @Override
    public Schedule deleteSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully deleted schedule data with ID : {}", schedule.getId());

        scheduleRepository.delete(schedule);

        return schedule;
    }

    @Override
    public List<Schedule> getAllSchedules() {

        log.info("Has successfully retrieved all schedules data");

        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully retrieved schedule data with ID : {}", schedule.getId());

        return schedule;
    }

    ModelMapper mapper = new ModelMapper();

    @Override
    public ScheduleDTO mapToDto(Schedule schedule) {
        return mapper.map(schedule, ScheduleDTO.class);
    }

    @Override
    public Schedule mapToEntity(ScheduleDTO scheduleDTO) {
        return mapper.map(scheduleDTO, Schedule.class);
    }

}
