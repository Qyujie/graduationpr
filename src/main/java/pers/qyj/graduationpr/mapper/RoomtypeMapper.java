package pers.qyj.graduationpr.mapper;

import java.util.List;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.RoomtypeExample;

public interface RoomtypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    int insert(Roomtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    int insertSelective(Roomtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    List<Roomtype> selectByExample(RoomtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    Roomtype selectByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Roomtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roomtype
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Roomtype record);
}