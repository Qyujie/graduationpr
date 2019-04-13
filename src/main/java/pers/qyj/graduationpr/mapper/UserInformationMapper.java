package pers.qyj.graduationpr.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pers.qyj.graduationpr.pojo.UserInformation;

@Mapper
public interface UserInformationMapper {
   
    @Insert(" insert into userInformation (name) values (#{name})")
    public void saveUserInformation(UserInformation userInformation);//添加用户信息
    
    @Update("update userInformation set "
            + "birthday=#{birthday},"
            + "sex=#{sex},"
            + "phone=#{phone},"
            + "prefecture=#{prefecture},"
            + "real_name=#{real_name},"
            + "id_card=#{id_card} "
    		+ "where name=#{username} ")
    public void updateUserInformation(UserInformation userInformation,String username);
    
    @Select("select * from userinformation  where name = #{username}")
    public UserInformation getUserInformation(String username); //查询用户信息
    
    @Update("update userInformation set head_portrait=#{head_portrait} where name=#{name} ")
    public void updateHeadPortrait(UserInformation userInformation);
    
}
