package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface InvitationMapper {

    @Select("SELECT * from invitation_ WHERE id = #{id}")
    @Results({
            @Result(property = "event", column = "event_id",
                    one = @One(select = "se.expleostockholm.signup.repository.EventMapper.getEventById")),
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    Optional<Invitation> getInvitationById(Long id);

    @Select("SELECT * FROM invitation_ WHERE event_id = #{event_id}")
    @Results({
            @Result(property = "event", column = "event_id",
                    one = @One(select = "se.expleostockholm.signup.repository.EventMapper.getEventById")),
            @Result(property = "guest", column = "guest_id",
                one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    List<Invitation> getInvitationsByEventId(Long event_id);

    @Select("SELECT * FROM invitation_ WHERE guest_id = #{guest_id}")
    @Results({
            @Result(property = "event", column = "event_id",
                    one = @One(select = "se.expleostockholm.signup.repository.EventMapper.getEventById")),
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    List<Invitation> getInvitationsByGuestId(Long guest_id);

    @Insert("INSERT INTO invitation_ (event_id, guest_id) VALUES (#{event.id}, #{guest.id})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void createInvitation(Invitation invitation);

    @Update("UPDATE invitation_ SET attendance=#{attendance}::attendance_, comment=#{comment} WHERE id=#{id}")
    void updateInvitation(Invitation invitation);

    @Delete("DELETE FROM invitation_ WHERE id=#{id}")
    void deleteInvitation(Invitation invitation);
}