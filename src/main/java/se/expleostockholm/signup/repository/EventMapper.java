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
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEvents();

    @Select("SELECT * FROM event_ WHERE id = #{event_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    Optional<Event> getEventById(Long event_id);

    @Select("SELECT * FROM event_ WHERE host_id = #{host_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEventsByHostId(Long host_id);
}
