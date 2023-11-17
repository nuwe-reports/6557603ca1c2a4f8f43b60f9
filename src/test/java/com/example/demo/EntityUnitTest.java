package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor doctor;

    private Patient patient;

    private Room room;

    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;
    private LocalDateTime startsAt;
    private LocalDateTime finishesAt;

    @BeforeEach
    void setUp(){
        doctor = new Doctor("John", "Olaya", 35, "j.olaya@email.com");
        patient = new Patient("Alice", "Johnson", 28, "alice@eemail.com");
        room = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        startsAt = LocalDateTime.parse("18:30 24/04/2023", formatter);
        finishesAt = LocalDateTime.parse("19:30 24/04/2023", formatter);
        appointment1 = new Appointment(patient,doctor,room,startsAt,finishesAt);
        appointment2 = new Appointment(patient,doctor,room,startsAt,finishesAt);
        appointment3 = new Appointment(patient,doctor,room,startsAt,finishesAt);
    }

    //Doctor test
    @Test
    void should_created_doctor(){
        Doctor doctorCreated = entityManager.persist(doctor);
        Assertions.assertNotNull(doctor);
        assertThat(doctor).isEqualTo(doctorCreated);
    }

    @Test
    void should_created_doctor_whit_null_attributes(){
        Doctor doctorEmpty = new Doctor();

        Assertions.assertNotNull(doctorEmpty);
        assertThat(doctorEmpty.getFirstName()).isEqualTo(null);
        assertThat(doctorEmpty.getLastName()).isEqualTo(null);
        assertThat(doctorEmpty.getEmail()).isEqualTo(null);
        assertThat(doctorEmpty.getAge()).isEqualTo(0);
    }

    @Test
    void should_created_doctor_with_negative_age(){
        doctor.setAge(-10);

        assertThat(doctor.getAge()).isLessThan(0);
    }

    @Test
    void should_created_doctor_with_age_less_18(){
        doctor.setAge(5);
        Doctor doctorNew = new Doctor();
        doctorNew.setAge(18);

        assertThat(doctor.getAge()).isLessThan(18);
        assertThat(doctorNew.getAge()).isGreaterThanOrEqualTo(18);
    }

    @Test
    void should_created_doctor_without_email_format(){
        doctor.setEmail("fail_email.com");
        String emailFormat = "^[A-Za-z0-9+_.-]+@(.+)$";

        Assertions.assertFalse(doctor.getEmail().matches(emailFormat));
    }

    @Test
    void should_access_doctor_attributes(){
        assertThat(doctor.getFirstName()).isEqualTo("John");
        assertThat(doctor.getLastName()).isEqualTo("Olaya");
        assertThat(doctor.getAge()).isEqualTo(35);
        assertThat(doctor.getEmail()).isEqualTo("j.olaya@email.com");

    }
    @Test
    void should_modify_doctor_attributes(){
        doctor.setAge(20);
        doctor.setEmail("olaya@gmail.com");
        doctor.setLastName("Velez");
        doctor.setFirstName("Carlos");
        doctor.setId(05);

        assertThat(doctor.getFirstName()).isEqualTo("Carlos");
        assertThat(doctor.getFirstName()).isNotEqualTo("John");
        assertThat(doctor.getLastName()).isEqualTo("Velez");
        assertThat(doctor.getLastName()).isNotEqualTo("Olaya");
        assertThat(doctor.getAge()).isEqualTo(20);
        assertThat(doctor.getAge()).isNotEqualTo(35);
        assertThat(doctor.getEmail()).isNotEqualTo("j.olaya@email.com");
        assertThat(doctor.getEmail()).isEqualTo("olaya@gmail.com");
        assertThat(doctor.getId()).isNotEqualTo(20);
        assertThat(doctor.getId()).isEqualTo(05);

    }

    //Patient test

    @Test
    void should_created_patient(){
        Patient patientCreated = entityManager.persist(patient);
        Assertions.assertNotNull(patient);
        assertThat(patient).isEqualTo(patientCreated);
    }

    @Test
    void should_created_patient_whit_null_attributes(){
        Patient patientEmpty = new Patient();

        Assertions.assertNotNull(patientEmpty);
        assertThat(patientEmpty.getFirstName()).isEqualTo(null);
        assertThat(patientEmpty.getLastName()).isEqualTo(null);
        assertThat(patientEmpty.getEmail()).isEqualTo(null);
        assertThat(patientEmpty.getAge()).isEqualTo(0);
    }

    @Test
    void should_patient_doctor_with_negative_age(){
        patient.setAge(-10);

        assertThat(patient.getAge()).isLessThan(0);
    }

    @Test
    void should_created_patient_with_age_less_18(){
        patient.setAge(5);
        Patient patientNew = new Patient();
        patientNew.setAge(18);

        assertThat(patient.getAge()).isLessThan(18);
        assertThat(patientNew.getAge()).isGreaterThanOrEqualTo(18);
    }

    @Test
    void should_created_patient_without_email_format(){
        patient.setEmail("fail_email.com");
        String emailFormat = "^[A-Za-z0-9+_.-]+@(.+)$";

        Assertions.assertFalse(patient.getEmail().matches(emailFormat));
    }

    @Test
    void should_access_patient_attributes(){
        assertThat(patient.getFirstName()).isEqualTo("Alice");
        assertThat(patient.getLastName()).isEqualTo("Johnson");
        assertThat(patient.getAge()).isEqualTo(28);
        assertThat(patient.getEmail()).isEqualTo("alice@eemail.com");

    }
    @Test
    void should_modify_patient_attributes(){

        patient.setAge(20);
        patient.setEmail("velez@gmail.com");
        patient.setLastName("Velez");
        patient.setFirstName("Maria");
        patient.setId(05);

        assertThat(patient.getFirstName()).isEqualTo("Maria");
        assertThat(patient.getFirstName()).isNotEqualTo("Alice");
        assertThat(patient.getLastName()).isEqualTo("Velez");
        assertThat(patient.getLastName()).isNotEqualTo("Johnson");
        assertThat(patient.getAge()).isEqualTo(20);
        assertThat(patient.getAge()).isNotEqualTo(28);
        assertThat(patient.getEmail()).isEqualTo("velez@gmail.com");
        assertThat(patient.getEmail()).isNotEqualTo("alice@eemail.com");
        assertThat(patient.getId()).isEqualTo(05);
        assertThat(patient.getId()).isNotEqualTo(10);


    }

    //Room test
    @Test
    void should_created_room(){
        Room roomCreated = entityManager.persist(room);
        Assertions.assertNotNull(room);
        assertThat(room).isEqualTo(roomCreated);
    }

    @Test
    void should_created_room_whit_null_name(){
        Room roomEmpty = new Room();

        Assertions.assertNotNull(roomEmpty);
        assertThat(roomEmpty.getRoomName()).isEqualTo(null);

    }

    @Test
    void should_created_room_whit_empty_name(){
        Room roomEmpty = new Room("");

        Assertions.assertNotNull(roomEmpty);
        assertThat(roomEmpty.getRoomName()).isEqualTo("");

    }

    @Test
    void should_access_room_attributes(){
        assertThat(room.getRoomName()).isEqualTo("Dermatology");
    }

    //Appointment test

    @Test
    void should_created_appointment(){
        Appointment appointmentCreated = entityManager.persist(appointment1);
        Assertions.assertNotNull(appointment1);
        assertThat(appointment1).isEqualTo(appointmentCreated);
    }

    @Test
    void should_compare_two_appointment(){
        Appointment appointmentCreated = entityManager.persist(appointment1);
        Appointment appointmentCreated2 = entityManager.persist(appointment2);

        assertThat(appointmentCreated).isNotEqualTo(appointmentCreated2);
    }

    @Test
    void should_compare_two_appointment_with_equal_attributes(){
        //Todo: The equals method should be implemented in the class so that it correctly compares all class attributes
        assertThat(appointment1).isNotEqualTo(appointment2);
    }

    @Test
    void should_created_appointment_with_null_attributes(){
        Appointment appointmentEmpty = new Appointment();

        assertThat(appointmentEmpty.getId()).isEqualTo(0);
        assertThat(appointmentEmpty.getRoom()).isNull();
        assertThat(appointmentEmpty.getDoctor()).isNull();
        assertThat(appointmentEmpty.getPatient()).isNull();
        assertThat(appointmentEmpty.getStartsAt()).isNull();
        assertThat(appointmentEmpty.getFinishesAt()).isNull();
    }

    @Test
    void should_access_appointment_attributes(){
        assertThat(appointment1.getPatient()).isEqualTo(patient);
        assertThat(appointment1.getDoctor()).isEqualTo(doctor);
        assertThat(appointment1.getRoom()).isEqualTo(room);
        assertThat(appointment1.getStartsAt()).isEqualTo(startsAt);
        assertThat(appointment1.getFinishesAt()).isEqualTo(finishesAt);
    }

    @Test
    void should_update_appointment_attributes(){
        Doctor newDoctor = new Doctor("Jose","Arias",30,"jose@email.com");
        Patient newPatient = new Patient("Maria","Gaviria",20,"maria@email.com");
        Room newRoom = new Room("Optometry");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime newStartsAt = LocalDateTime.parse("14:30 24/08/2023", formatter);
        LocalDateTime newFinishesAt = LocalDateTime.parse("15:00 24/08/2023", formatter);

        appointment1.setId(20);
        appointment1.setDoctor(newDoctor);
        appointment1.setRoom(newRoom);
        appointment1.setPatient(newPatient);
        appointment1.setStartsAt(newStartsAt);
        appointment1.setFinishesAt(newFinishesAt);

        assertThat(appointment1.getPatient()).isEqualTo(newPatient);
        assertThat(appointment1.getDoctor()).isEqualTo(newDoctor);
        assertThat(appointment1.getRoom()).isEqualTo(newRoom);
        assertThat(appointment1.getStartsAt()).isEqualTo(newStartsAt);
        assertThat(appointment1.getFinishesAt()).isEqualTo(newFinishesAt);
        assertThat(appointment1.getId()).isEqualTo(20);
    }

    @Test
    void should_check_overlap_startsAt_attribute(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime newFinishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        appointment1.setFinishesAt(newFinishesAt);

        assertThat(appointment1.overlaps(appointment2)).isTrue();
    }

    @Test
    void should_check_overlap_finishesAt_attribute(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime newStartsAt = LocalDateTime.parse("14:30 24/08/2023", formatter);

        appointment1.setStartsAt(newStartsAt);

        assertThat(appointment1.overlaps(appointment2)).isTrue();
    }

    @Test
    void should_overlaps_when_new_appointment_finish_between_current(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime newStartAt = LocalDateTime.parse("18:00 24/04/2023", formatter);
        LocalDateTime newFinishAt = LocalDateTime.parse("19:00 24/04/2023", formatter);

        appointment2.setStartsAt(newStartAt);
        appointment2.setFinishesAt(newFinishAt);

        assertThat(appointment1.overlaps(appointment2)).isTrue();
    }
    @Test
    void should_overlaps_when_new_appointment_starts_between_current(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime newStartsAt = LocalDateTime.parse("19:00 24/04/2023", formatter);
        LocalDateTime newFinishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        appointment2.setStartsAt(newStartsAt);
        appointment2.setFinishesAt(newFinishesAt);

        assertThat(appointment1.overlaps(appointment2)).isTrue();
    }

    @Test
    void should_no_overlaps(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime newStartsAt = LocalDateTime.parse("17:00 24/04/2023", formatter);
        LocalDateTime newFinishesAt = LocalDateTime.parse("18:00 24/04/2023", formatter);

        appointment2.setStartsAt(newStartsAt);
        appointment2.setFinishesAt(newFinishesAt);

        assertThat(appointment1.overlaps(appointment2)).isFalse();
    }

    @Test
    void should_no_overlaps_with_equals_dates_and_diferents_rooms(){
        Room newRoom = new Room("Optometry");
        appointment3 = new Appointment(patient,doctor,newRoom,startsAt,finishesAt);

        assertThat(appointment1.overlaps(appointment3)).isFalse();
    }



}
