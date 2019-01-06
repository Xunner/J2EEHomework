package service;

import vo.PayVO;

import java.util.Map;

/**
 * 订单Service
 * <br>
 * created on 2019/01/06
 *
 * @author 巽
 **/
public interface OrderService {
	PayVO generateOrder(String userId, Map<Integer, Integer> cart);
}
