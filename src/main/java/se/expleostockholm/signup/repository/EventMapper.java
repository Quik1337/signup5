package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Event;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface EventMapper {

    @Select("SELECT * FROM event_")
    @Results({
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getAllEvents();

    @Insert("INSERT INTO event_ (host_id, title, description, date_of_event, time_of_event, location) VALUES (#{host_id}, #{title}, #{description}, #{date_of_event}, #{time_of_event}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long createEvent(Event event);


    @Select("SELECT * FROM event_ WHERE id = #{event_id}")
    @Results({
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getAccountById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    Optional<Event> getEventById(Long event_id);

    @Delete("DELETE FROM event_ WHERE id = #{id}")
    void removeEventById(Long id);

    @Select("SELECT COUNT(*) FROM event_ WHERE host_id=#{host.id} and title=#{title} and date_of_event=#{date_of_event} and time_of_event=#{time_of_event} and location=#{location}")
    Long isDuplicateEvent(Event event);

    @Select("SELECT * FROM event_ WHERE host_id = #{id}")
    @Results({

            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getAccountById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEventsByHostId(Long id);
}
