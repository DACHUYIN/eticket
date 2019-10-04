/**
 * 计算电子券码的最终价格
 * 
 * @param {Object} totalPrice 出售价格（含手续费）
 * @param {Object} price 价格
 * @param {Object} memberShipLevel 会员等级
 */
export function calculatePrice(price, memberShipLevel) {
	let serviceCharge;
	if (price >= 0 && price < 10) {
		serviceCharge = getMemberShipDiscount(0.1, memberShipLevel);
	} else if (price >= 10 && price < 100) {
		serviceCharge = getMemberShipDiscount(0.5, memberShipLevel);
	} else if (price >= 100 && price < 500) {
		serviceCharge = getMemberShipDiscount(1, memberShipLevel);
	} else if (price >= 500 && price < 1000) {
		serviceCharge = getMemberShipDiscount(2, memberShipLevel);
	} else if (price >= 1000) {
		serviceCharge = getMemberShipDiscount(5, memberShipLevel);
	}
	return parseFloat(parseFloat(price) + parseFloat(serviceCharge));
}

function getMemberShipDiscount(charge, memberShipLevel) {
	if(memberShipLevel.valueOf() === 'NORMAL') {
		return parseFloat(charge * 0.99);
	} else if (memberShipLevel.valueOf() === 'SILVER') {
		return parseFloat(charge * 0.97);
	} else if (memberShipLevel.valueOf() === 'GOLDEN') {
		return parseFloat(charge * 0.95);
	} else if (memberShipLevel.valueOf() === 'DIAMOND') {
		return parseFloat(charge * 0.90);
	}
}
