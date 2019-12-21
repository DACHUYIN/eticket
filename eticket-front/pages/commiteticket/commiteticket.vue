<template>
	<view>
		<cu-custom bgColor="bg-gradual-green" :isBack="true">
			<block slot="backText">
				<text class="text-xl">返回</text>
			</block>
			<block slot="content">
				<text class="text-xl">发布</text>
			</block>
		</cu-custom>
		<view v-if="submitSuccess == '0'">
			<form>
				<radio-group class="block" @change="RadioChange">
					<view class="form-group-component">
						<radio :class="radio=='SELL'?'checked':''" :checked="radio=='SELL'?true:false" value="SELL">
						</radio>
						<text class="text-xl" style="margin-left: 10upx;">出售</text>
						<radio :class="radio=='BUY'?'checked':''" :checked="radio=='BUY'?true:false" value="BUY" style="margin-left: 50upx;">
						</radio>
						<text class="text-xl" style="margin-left: 10upx;">求购</text>
					</view>
				</radio-group>
				<view class="form-group-picker">
					<view class="title text-xl">券码种类</view>
					<picker @change="PickerChange" :value="index" :range="picker">
						<view class="picker text-xl">
							{{index>-1?picker[index]:'禁止换行，超出容器部分会以 ... 方式截断'}}
						</view>
					</picker>
				</view>
				<view class="form-group-component">
					<view class="title text-xl" style="width: 16%;">标题</view>
					<input class="text-xl" placeholder="请输入想出售或者求购的券码名称" name="input" style="width: 84%;" @input="GetTypeName"></input>
				</view>
				<view class="form-group-component">
					<view class="title text-xl" style="width: 16%;">有效期</view>
					<picker mode="date" :value="startDate" @change="StartDateChange" class="datetime-picker" style="padding-left: 50upx;padding-right: 20upx;">
						<view class="picker">
							{{startDate}}
						</view>
					</picker>
					<text class="text-xl" style="padding-right: 20upx;">～</text>
					<picker mode="date" :value="endDate" @change="EndDateChange" class="datetime-picker">
						<view class="picker">
							{{endDate}}
						</view>
					</picker>
				</view>
				<view class="cu-bar bg-white">
					<view class="action">
						<text class="text-xl">二维码上传</text>
					</view>
					<view class="action">
						<text class="text-xl">{{imgList.length}}/4</text>
					</view>
				</view>
				<view class="form-group-component">
					<view class="grid col-4 grid-square flex-sub">
						<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
							<image :src="imgList[index]" mode="aspectFill"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="index">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImage" v-if="imgList.length<4">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="form-group-component">
					<view class="title text-xl" style="width: 16%;">券码</view>
					<input class="text-xl" type="text" placeholder="请输入数字或者英文串码" name="input" style="width: 84%;" @input="GetQrCode"></input>
				</view>
				<view class="form-group-component">
					<view class="title text-xl" style="width: 16%;">价格</view>
					<input class="text-xl text-right" name="input" style="width: 84%;padding-right: 10upx;" @input="GetPrice"></input>
					<text class="text-xl">元</text>
				</view>
				<view class="form-group-component">
					<view class="title text-xl" style="width: 50%;">出售价格(含手续费)</view>
					<input class="text-xl text-right" name="input" style="width: 50%;padding-right: 10upx;" :value="totalPrice"
					 disabled="true"></input>
					<text class="text-xl">元</text>
				</view>
				<view class="">
					<button class="bg-green" style="margin-left: 40upx;margin-right: 40upx;margin-top: 100upx;" @click="CommitEticket">确认发布</button>
				</view>
			</form>
		</view>
		<view v-if="submitSuccess == '1'">
			<view class="padding flex flex-direction flex-sub text-center">
				<view class="flex padding justify-center">
					<image src="/static/commonImg/submitsuccess.png" class="img"></image>
				</view>
				<text class="text-black text-lg">券码提交成功～</text>
			</view>
			<view class="flex flex-direction text-center text-center" style="margin-top: 600upx;">
				<navigator url="../index/index">
					<text class="text-blue text-lg">返回首页</text>
				</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		UPLOAD_FILE_URL,
		SUBMIT_ETICKET_URL
	} from '../../utils/api.js';
	import {
		dateFormat
	} from '../../utils/dateFormat.js';
	import {
		calculatePrice
	} from '../../utils/calculatePrice.js'
	export default {
		data() {
			return {
				index: 0,
				picker: ['餐饮券', '影音娱乐券', '景点门票', '电影票', '演唱会门票', '展览门票'],
				radio: 'SELL', // S:出售 B:求购 进入画面出售按钮处于Checked状态
				startDate: dateFormat('YYYY-mm-DD', new Date()),
				endDate: dateFormat('YYYY-mm-DD', new Date(new Date().getTime() + 24 * 60 * 60 * 1000)),
				imgList: [],
				ticketType: 'FOOD',
				ticketName: '',
				qrCode: '',
				price: '',
				totalPrice: '',
				submitSuccess: '0'
			}
		},
		methods: {
			RadioChange: function(e) {
				this.radio = e.detail.value;
			},
			PickerChange: function(e) {
				this.index = e.detail.value;
				switch (index) {
					case 0:
						ticketType = 'FOOD'; // 餐饮券
						break;
					case 1:
						ticketType = 'ENTERTAINMENT'; // 影音娱乐券
						break;
					case 2:
						ticketType = 'ADMINSION'; // 景点门票
						break;
					case 3:
						ticketType = 'FILM'; // 电影票
						break;
					case 4:
						ticketType = 'CONCERT'; // 演唱会门票
						break;
					case 5:
						ticketType = 'EXHIBITION'; // 展览门票
						break;
					default:
						ticketType = 'FOOD';
						break;
				};
			},
			GetPrice: function(e) {
				this.price = e.detail.value
					.replace(/[^\d.]/g, '') // 清除“数字”和“.”以外的字符
					.replace(/\.{2,}/g, '.') // 只保留第一个. 清除多余的
					.replace('.', '$#$')
					.replace(/\./g, '')
					.replace('$#$', '.')
					.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
				if (this.price.indexOf('.') < 0 && this.price != '') {
					// 以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
					this.price = parseFloat(this.price);
				};
				let user = uni.getStorageSync('user');
				this.totalPrice = calculatePrice(this.price, user.memberShipLevel);
				this.totalPrice = this.totalPrice.toFixed(3);
				if (this.totalPrice.indexOf('.') > -1) {
					// 最终价格保留2位小数，但是只做舍弃处理，不做进1位处理
					let index = this.totalPrice.indexOf('.');
					this.totalPrice = this.totalPrice.substring(0, index + 3);
				}
				return {
					value: this.price,
				};
			},
			GetQrCode: function(e) {
				// 限制二维码串只能输入英文和数字
				this.qrCode = e.detail.value.replace(/[^\a-\z\A-\Z0-9]/g, '');
				return {
					value: this.qrCode,
				};
			},
			GetTypeName: function(e) {
				this.ticketName = e.detail.value;
			},
			StartDateChange: function(e) {
				this.startDate = e.detail.value;
			},
			EndDateChange: function(e) {
				this.endDate = e.detail.value;
			},
			ChooseImage: function(e) {
				uni.chooseImage({
					count: 4, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						const tempFilePaths = res.tempFilePaths;
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(tempFilePaths);
						} else {
							this.imgList = tempFilePaths;
						}
					}
				});
			},
			ViewImage: function(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg: function(e) {
				uni.showModal({
					content: '确定要删除这张二维码截图吗？',
					confirmText: '确认',
					cancelText: '取消',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			CommitEticket: function(e) {
				let user = uni.getStorageSync('user');
				let wechatOpenId = user.wechatOpenId;
				let telephoneNumber = user.telephoneNumber;
				var count = 0;
				var imgList = this.imgList;
				this.submitSuccess = 1;
				var idempotentToken = '';
				uni.request({
					url:GET_IDEMPOTENT_TOKEN,
					method: 'GET',
					header: {
						'Content-Type': 'application/json',
						'X-Token': uni.getStorageSync('token').token
					},
					success: (res) => {
						let resData = res.data;
						if (resData.responseCode == '400') {
							// 券码提交成功
					        idempotentToken=resData.idempotentToken;
						}
					},
					fail: (res) => {
						uni.showModal({
							title: '错误',
							content: '系统异常，请再次点击确认发布！',
							showCancel: false
						})
					}
				});
				for (var i = 0; i < imgList.length; i++) {
					uni.uploadFile({
						url: UPLOAD_FILE_URL,
						filePath: imgList[i],
						name: 'uploadFile',
						formData: {
							'wechatOpenId': wechatOpenId,
							'ticketType': this.ticketType
						},
						header: {
							"Content-Type": "multipart/form-data",
							'X-Token': uni.getStorageSync('token').token,
							'idempotent-token':idempotentToken,
						},
						success: (uploadFileRes) => {
							count++;
							let uploadFileResData = JSON.parse(uploadFileRes.data);
							if (count == imgList.length && uploadFileResData.responseCode === '200') {
								uni.request({
									url:GET_IDEMPOTENT_TOKEN,
									method: 'GET',
									header: {
										'Content-Type': 'application/json',
										'X-Token': uni.getStorageSync('token').token
									},
									success: (res) => {
										let resData = res.data;
										if (resData.responseCode == '400') {
											// 券码提交成功
									        idempotentToken=resData.idempotentToken;
										}
									},
									fail: (res) => {
										uni.showModal({
											title: '错误',
											content: '系统异常，请再次点击确认发布！',
											showCancel: false
										})
									}
								});
								uni.request({
									url: SUBMIT_ETICKET_URL,
									method: 'POST',
									data: {
										'wechatOpenIdSeller': wechatOpenId,
										'telephoneNumber': telephoneNumber,
										'price': this.price,
										'totalPrice': this.totalPrice,
										'imgAddress': uploadFileResData.imgAddress,
										'uploadFlag': 1,
										'qrCode': this.qrCode,
										'startDate': this.startDate,
										'endDate': this.endDate,
										'orderType': this.radio,
										'ticketName': this.ticketName,
										'ticketType': this.ticketType,
										'orderStatus': this.radio === 'SELL' ? '1' : '0', // 订单状态，刚发布完的券码要么是出售中要么是求购中
										'sqlMethod': 1, // 发布券码，即插入操作
									},
									header: {
										'Content-Type': 'application/json',
										'X-Token': uni.getStorageSync('token').token,
										'idempotent-token':idempotentToken,
									},
									success: (res) => {
										let resData = res.data;
										if (resData.responseCode == '300') {
											// 券码提交成功
                                            
										}
									},
								});
							}
						},
						fail: (res) => {
							uni.showModal({
								title: '错误',
								content: '二维码图片上传失败，请再次点击确认发布！',
								showCancel: false
							})
						}
					});
				}
			}
		}
	}
</script>

<style scoped>
	.img {
		width: 160upx;
		height: 160upx;
		border-radius: 50%;
	}

	.price-input {
		content: "¥";
		margin-right: 4upx;
	}

	.datetime-picker {
		font-size: 36upx;
		display: flex;
		justify-content: center;
		text-align: justify;
		position: relative;
		padding-top: 7upx;
	}

	.form-group-component {
		background-color: #ffffff;
		display: flex;
		align-items: center;
		padding: 10upx 30upx;
		border-bottom: 1upx solid #eee;
	}

	.form-group-picker {
		background-color: #ffffff;
		display: flex;
		align-items: center;
		padding: 10upx 30upx;
		justify-content: space-between;
		border-bottom: 1upx solid #eee;
	}

	.form-group-picker picker {
		flex: 1;
		padding-right: 40upx;
		overflow: hidden;
		position: relative;
	}

	.form-group-picker picker .picker {
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
		width: 100%;
		text-align: right;
	}

	.form-group-picker picker::after {
		font-family: cuIcon;
		display: flex;
		content: "\e6a3";
		position: absolute;
		color: #8799a3;
		width: 45upx;
		align-items: center;
		top: 0;
		bottom: 0;
		right: -20upx;
		margin: auto;
	}
</style>
