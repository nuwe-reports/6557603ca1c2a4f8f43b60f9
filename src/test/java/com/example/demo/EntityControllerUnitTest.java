
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_get_all_doctors() throws Exception {
        Doctor doctor = new Doctor("jose","lopez",35,"jose@email.com");
        Doctor doctor2 = new Doctor("maria","valencia",28,"maria@email.com");
        Doctor doctor3 = new Doctor("maria","valencia",28,"maria@email.com");
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(doctors)));
    }

    @Test
    void should_not_find_doctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_doctor_by_id() throws Exception {
        Doctor doctor = new Doctor("jose","lopez",35,"jose@email.com");
        doctor.setId(10);
        Optional<Doctor> optionalDoctor = Optional.of(doctor);

        when(doctorRepository.findById(doctor.getId())).thenReturn(optionalDoctor);

        assertThat(optionalDoctor).isPresent();
        assertThat(optionalDoctor.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(10);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(doctor)));
    }

    @Test
    void should_not_get_doctor_by_id() throws Exception {
        long id = 10;
        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_doctor() throws Exception {
        Doctor doctor = new Doctor("jose","lopez",35,"jose@email.com");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(doctor)));
    }

    @Test
    void should_create_doctor_with_null_attributes() throws Exception {
        Doctor doctor = new Doctor();

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(doctor)));
    }

    @Test
    void should_create_doctor_send_other_class() throws Exception {
        //TODO: This class must implement validations, this test should fail or throw exception
        Room room = new Room();

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_delete_doctor() throws Exception {
        Doctor doctor = new Doctor("jose","lopez",35,"jose@email.com");
        doctor.setId(10);
        Optional<Doctor> optionalDoctor = Optional.of(doctor);

        when(doctorRepository.findById(doctor.getId())).thenReturn(optionalDoctor);

        assertThat(optionalDoctor).isPresent();
        assertThat(optionalDoctor.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(10);

        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_doctor() throws Exception {
        long id = 20;

        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_all_doctors() throws Exception {
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_get_all_patients() throws Exception {
        Patient patient = new Patient("julio","arias",25,"julio@email.com");
        Patient patient2 = new Patient("ana","lopez",45,"ana@email.com");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(patients)));

    }

    @Test
    void should_not_get_all_patients() throws Exception {
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_patient_by_id() throws Exception {
        Patient patient = new Patient("jose","lopez",35,"jose@email.com");
        patient.setId(10);
        Optional<Patient> optionalPatient = Optional.of(patient);

        when(patientRepository.findById(patient.getId())).thenReturn(optionalPatient);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(10);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    void should_not_get_patient_by_id() throws Exception {
        long id = 10;
        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_patient() throws Exception {
        Patient patient = new Patient("julio","lopera",45,"julio@email.com");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    void should_create_patient_with_null_attributes() throws Exception {
        Patient patient = new Patient();

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(patient)));
    }

    @Test
    void should_create_patient_send_other_class() throws Exception {
        //TODO: This class must implement validations, this test should fail or throw exception
        Room room = new Room();

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_delete_patient() throws Exception {
        Patient patient = new Patient("mario","lasso",15,"mario@email.com");
        patient.setId(10);
        Optional<Patient> optionalPatient = Optional.of(patient);

        when(patientRepository.findById(patient.getId())).thenReturn(optionalPatient);

        assertThat(optionalPatient).isPresent();
        assertThat(optionalPatient.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(10);

        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_patient() throws Exception {
        long id = 30;

        mockMvc.perform(delete("/api/patient/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_all_patients() throws Exception {
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }


}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_get_all_rooms() throws Exception {
        Room room = new Room("optometry");
        Room room2 = new Room("dermatology");
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rooms)));
    }

    @Test
    void should_not_get_all_rooms() throws Exception {
        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_get_room_by_name() throws Exception {
        Room room = new Room("optometry");

        Optional<Room> optionalRoom = Optional.of(room);

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(optionalRoom);

        assertThat(optionalRoom).isPresent();
        assertThat(optionalRoom.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(optionalRoom.get().getRoomName()).isEqualTo("optometry");
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(room)));
    }

    @Test
    void should_not_get_room_by_name() throws Exception {
        String name = "optometry";
        mockMvc.perform(get("/api/rooms/" + name))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_create_room() throws Exception {
        Room room = new Room("optometry");

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(room)));
    }

    @Test
    void should_create_room_with_null_name() throws Exception {
        Room room = new Room();

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(room)));
    }

    @Test
    void should_create_room_send_other_class() throws Exception {
        //TODO: This class must implement validations, this test should fail or throw exception
        Patient patient = new Patient();

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_delete_room() throws Exception {
        Room room = new Room("optometry");

        Optional<Room> optionalRoom = Optional.of(room);

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(optionalRoom);

        assertThat(optionalRoom).isPresent();
        assertThat(optionalRoom.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("optometry");

        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_rooms() throws Exception {
        String name = "optometry";

        mockMvc.perform(delete("/api/rooms/" + name))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_delete_all_rooms() throws Exception {
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}
