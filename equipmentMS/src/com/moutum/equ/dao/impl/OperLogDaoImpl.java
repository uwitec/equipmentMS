package com.moutum.equ.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moutum.equ.base.DaoSupportImpl;
import com.moutum.equ.dao.OperLogDao;
import com.moutum.equ.domain.OperLog;

/************************************************************************************
 * @Title        : OperLogDaoImpl.java
 * @Description : 
 * @Author       : BianWeiqing
 * @DateTime     : 2015年3月17日 下午5:12:09
 * @Copyright    : 2015 Moutum All Rights Reserved
 * @version      : V1.0
 ************************************************************************************/
@Repository @Transactional
public class OperLogDaoImpl extends DaoSupportImpl<OperLog> implements OperLogDao
{
    @Resource SessionFactory sessionFactory;
    
    @Override
    public int getRecordCount(Map<String, Object> map)
    {
        Map<Integer, Object> valueMap = new HashMap<Integer, Object>();
        int index = 0;
        String hql = "SELECT COUNT(o) FROM OperLog o WHERE 1 = 1";
        
        if(null != map.get("operType"))
        {
            hql += " AND o.operType = ?";
            valueMap.put(index++, map.get("operType"));
        }
        if(null != map.get("operator"))
        {
            hql += " AND o.operator like ?";
            valueMap.put(index++, map.get("operator"));
        }
        if(null != map.get("operLogTimeST"))
        {
            hql += " AND o.operLogTime > ?";
            valueMap.put(index++, map.get("operLogTimeST"));
        }
        if(null != map.get("operLogTimeED"))
        {
            hql += " AND o.operLogTime < ?";
            valueMap.put(index++, map.get("operLogTimeED"));
        }
        
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        
        for(int i = 0; i < valueMap.size(); i++)
        {
            query.setParameter(i, valueMap.get(i));
        }
        return Integer.parseInt(query.list().get(0).toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OperLog> getRecordList(int pageNum, int pageSize,Map<String, Object> map)
    {
        Map<Integer, Object> valueMap = new HashMap<Integer, Object>();
        int index = 0;
        String hql = "FROM OperLog o WHERE 1 = 1";
        
        if(null != map.get("operType"))
        {
            hql += " AND o.operType = ?";
            valueMap.put(index++, map.get("operType"));
        }
        if(null != map.get("operator"))
        {
            hql += " AND o.operator like ?";
            valueMap.put(index++, map.get("operator"));
        }
        if(null != map.get("operLogTimeST"))
        {
            hql += " AND o.operLogTime > ?";
            valueMap.put(index++, map.get("operLogTimeST"));
        }
        if(null != map.get("operLogTimeED"))
        {
            hql += " AND o.operLogTime < ?";
            valueMap.put(index++, map.get("operLogTimeED"));
        }
        
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        
        for(int i = 0; i < valueMap.size(); i++)
        {
            query.setParameter(i, valueMap.get(i));
        }
        query.setFirstResult(pageSize*(pageNum - 1));
        query.setMaxResults(pageSize);
        return query.list();
    }
}