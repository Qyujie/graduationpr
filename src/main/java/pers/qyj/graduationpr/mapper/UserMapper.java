package pers.qyj.graduationpr.mapper;

import pers.qyj.graduationpr.pojo.User;
import pers.qyj.graduationpr.pojo.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectByExample(UserExample example);

	User selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	@Select("select * from user ")
	List<User> findAll();

	@Insert(" insert into user ( name,password,salt ) values (#{name},#{password},#{salt}) ")
	public void save(User user);

	@Delete(" delete from user where id= #{id} ")
	public void delete(int id);

	@Select("select * from user where name= #{name} ")
	public User getUserByName(String name);

	@Select("select * from user where id= #{id} ")
	public User getUserById(int id);
	
	@Select("select * from user where name= #{name} ")
	public User getUserId(String name);

	@Update("update user set name=#{name} where id=#{id} ")
	public int update(User user);

	@Update("update user set password=#{password} where id=#{id} ")
	public int setPassword(User user);

	@Select("select * from user  where name = #{name} and password = #{password}")
	public User verification(User user); // 密码验证

	@Select("select * from user  where name = #{name}")
	public User checkName(User user); // 重名验证
}