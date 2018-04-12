package net.onebean.kepler.service.impl;
import org.springframework.stereotype.Service;
import net.onebean.core.BaseBiz;
import net.onebean.kepler.model.TestDp;
import net.onebean.kepler.service.TestDpService;
import net.onebean.kepler.dao.TestDpDao;

@Service
public class TestDpServiceImpl extends BaseBiz<TestDp, TestDpDao> implements TestDpService{
}