package se.redvet.app.domain.user;

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

public class InMemoryUserRepo implements UserRepo {
    Map<Integer, User> database = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @Override
    public Optional<User> findByUsernameOrEmail(String search) {
        return database.values().stream()
                .filter(user -> user.getUsername().equals(search) || user.getEmail().equals(search))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return database.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void removeByUsername(String username) {
        database.values().removeIf(user -> user.getUsername().equals(username));
    }

    @Override
    public <S extends User> S save(S entity) {
        if (entity.getId() != null) {
            User existUser = database.get(entity.getId());
            if (existUser != null) {
                validateUniqueFieldsForExistingUser(entity);

                existUser.setUsername(entity.getUsername());
                existUser.setFirstName(entity.getFirstName());
                existUser.setLastName(entity.getLastName());
                existUser.setPassword(entity.getPassword());
                existUser.setEmail(entity.getEmail());
                database.put(existUser.getId(), existUser);
                return (S) existUser;
            } else {
                throw new UserNotFoundException("User with ID " + entity.getId() + " not found");
            }
        } else {
            validateUniqueFieldsForNewUser(entity);
            User user = User.builder()
                    .id(idCounter.incrementAndGet())
                    .username(entity.getUsername())
                    .firstName(entity.getFirstName())
                    .lastName(entity.getLastName())
                    .password(entity.getPassword())
                    .email(entity.getEmail())
                    .build();
            database.put(user.getId(), user);
            return (S) user;
        }
    }
    private void validateUniqueFieldsForExistingUser(User updatedUser) {
        if (database.values().stream().anyMatch(user ->
                (user.getUsername().equals(updatedUser.getUsername()) && !user.getId().equals(updatedUser.getId())))) {
            throw new UserNotUpdatedException("User not updated - duplicate username.");
        }
        if (database.values().stream().anyMatch(user ->
                (user.getEmail().equals(updatedUser.getEmail()) && !user.getId().equals(updatedUser.getId())))) {
            throw new UserNotUpdatedException("User not updated - duplicate email.");
        }
    }
    private void validateUniqueFieldsForNewUser(User newUser) {
        if (database.values().stream().anyMatch(user -> user.getUsername().equals(newUser.getUsername()))) {
            throw new UserNotCreatedException("User not created - duplicate username");
        }
        if (database.values().stream().anyMatch(user -> user.getEmail().equals(newUser.getEmail()))) {
            throw new UserNotCreatedException("User not created - duplicate email");
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Integer integer) {
        return null;
    }

    @Override
    public User getById(Integer integer) {
        return null;
    }

    @Override
    public User getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
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
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }
}
