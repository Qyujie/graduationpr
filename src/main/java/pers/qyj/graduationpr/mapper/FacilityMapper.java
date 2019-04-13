package pers.qyj.graduationpr.mapper;

import java.util.List;
import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.FacilityExample;

public interface FacilityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Facility record);

    int insertSelective(Facility record);

    List<Facility> selectByExample(FacilityExample example);

    Facility selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Facility record);

    int updateByPrimaryKey(Facility record);
}