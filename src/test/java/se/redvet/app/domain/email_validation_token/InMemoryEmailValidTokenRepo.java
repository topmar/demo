package se.redvet.app.domain.email_validation_token;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class InMemoryEmailValidTokenRepo implements EmailValidationTokenRepo {
    Map<String, EmailValidationToken> database = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @Override
    public Optional<EmailValidationToken> findByToken(String token) {
        return Optional.ofNullable(database.get(token));
    }

    @Override
    public void removeByToken(String token) {
        database.values().removeIf(emailToken -> emailToken.getToken().equals(token));
    }

    @Override
    public <S extends EmailValidationToken> S save(S entity) {
        database.put(entity.getToken(), entity);
        return (S) entity;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends EmailValidationToken> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends EmailValidationToken> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<EmailValidationToken> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public EmailValidationToken getOne(Integer integer) {
        return null;
    }

    @Override
    public EmailValidationToken getById(Integer integer) {
        return null;
    }

    @Override
    public EmailValidationToken getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends EmailValidationToken> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends EmailValidationToken> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends EmailValidationToken> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends EmailValidationToken> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends EmailValidationToken> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends EmailValidationToken> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends EmailValidationToken, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends EmailValidationToken> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<EmailValidationToken> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<EmailValidationToken> findAll() {
        return List.of();
    }

    @Override
    public List<EmailValidationToken> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(EmailValidationToken entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends EmailValidationToken> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<EmailValidationToken> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<EmailValidationToken> findAll(Pageable pageable) {
        return null;
    }
}