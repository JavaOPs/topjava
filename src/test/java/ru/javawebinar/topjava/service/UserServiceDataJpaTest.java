package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJpaTest extends AbstractUserServiceTest {
}