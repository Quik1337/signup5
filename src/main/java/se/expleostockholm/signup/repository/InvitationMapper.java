package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface InvitationMapper {

    @Select("SELECT * from invitation_ WHERE id = #{id}")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    Optional<Invitation> getInvitationById(Long id);

    @Update("UPDATE invitation_ SET attendance=#{attendance}::attendance WHERE id = #{invitation_id}")
    Long setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);

    @Select("SELECT * FROM invitation_")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    List<Invitation> getAllInvitations();

    @Select("SELECT * FROM invitation_ WHERE event_id = #{id}")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    List<Invitation> getInvitationsByEventId(Long id);

    @Insert("INSERT INTO invitation_ (guest_id, event_id, attendance) VALUES (#{guest.id}, #{event_id}, #{attendance}::attendance)")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long saveInvitation(Invitation invitation);

    @Select("SELECT COUNT(*) FROM invitation_ WHERE event_id=#{event_id} and guest_id=#{guest.id}")
    Long invitationExists(Invitation invitation);

    @Delete("DELETE FROM invitation_ WHERE event_id = #{id}")
    void removeInvitationByEventId(Long id);

    @Select("SELECT * FROM invitation_ WHERE guest_id = #{id}")
    List<Invitation> getInvitationsByGuestId(Long id);
}