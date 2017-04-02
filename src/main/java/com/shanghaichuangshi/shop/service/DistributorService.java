package com.shanghaichuangshi.shop.service;

import com.shanghaichuangshi.shop.dao.DistributorDao;
import com.shanghaichuangshi.shop.model.Distributor;
import com.shanghaichuangshi.service.Service;

import java.util.List;

public class DistributorService extends Service {

    private final DistributorDao distributorDao = new DistributorDao();

    public int count(Distributor distributor) {
        return distributorDao.count(distributor.getDistributor_name());
    }

    public List<Distributor> list(Distributor distributor, int m, int n) {
        return distributorDao.list(distributor.getDistributor_name(), m, n);
    }

    public Distributor find(String distributor_id) {
        return distributorDao.find(distributor_id);
    }

    public Distributor save(Distributor distributor, String request_user_id) {
        return distributorDao.save(distributor, request_user_id);
    }

    public boolean update(Distributor distributor, String request_user_id) {
        return distributorDao.update(distributor, request_user_id);
    }

    public boolean delete(Distributor distributor, String request_user_id) {
        return distributorDao.delete(distributor.getDistributor_id(), request_user_id);
    }

}