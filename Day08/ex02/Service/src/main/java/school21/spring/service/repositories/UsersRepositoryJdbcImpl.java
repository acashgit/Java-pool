package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(@Qualifier("usersRepositoryJdbc") DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email= ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("Wrong id");
            }
            user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2));
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(user);
    }

    @Override
    public String findPasswordById(Long id) {
        String password = "";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                password = resultSet.getString("password");
            }
            return  password;
        } catch (SQLException e) {
            try {
                throw new IllegalAccessException(e.getMessage());
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public void saveByEmail(String email, String password) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");
            statement.setString(1, email);
            statement.setString(2, password);
            if (statement.executeUpdate() == 0) {
                throw new IllegalAccessException();
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id= ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new RuntimeException("Wrong id");
           user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2));
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        ResultSet resultSet = null;
        try {
            resultSet = dataSource.getConnection().createStatement().executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                users.add(new User(resultSet.getLong(1),
                        resultSet.getString(2)));
            }
            dataSource.getConnection().createStatement().close();
            dataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User entity){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?, ?);");
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        try {
            Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE users SET email = ? WHERE id = ?;"
            );
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setLong(2, entity.getId());

            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?;"
        );
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
