<template name="personalcenter">
	<view>
		<view v-if="!user" class="user-no-login">
			<view class="padding flex flex-direction flex-sub text-center">
				<view class="flex padding justify-center">
					<image src="/static/img/unlogin.png" class="img"></image>
				</view>
				<text class="text-black text-lg">您好，只有登录后才可以使用哦～(^_−)☆</text>
				<button class="cu-btn bg-green margin-tb-sm lg" open-type="getUserInfo" @getuserinfo="wxGetUserInfo"
				 withCredentials="true">微信登录</button>
			</view>
		</view>
		<view v-if="user">
			<view class="bg-img flex align-center response" style="background-image: url(/static/img/personalcenter-background.png);height: 470upx;"
			 mode="widthFix">
				<view class="padding user-icon">
					<view class="cu-avatar round lg margin-bottom" style="width: 150rpx;height: 150rpx;" :style="[{ backgroundImage:'url(' + user.avatarUrl + ')' }]">
					</view>
					<text class="cu-tag radius margin-bottom transparent">{{user.nickName}}</text>
				</view>
				<!-- <view style='height:100%;width:100%;display:flex;flex-direction:column-reverse'>
				    <image src='/static/img/wave.gif' mode='scaleToFill' class='gif-black response' style='height:100rpx;'></image>
				</view> -->
			</view>
			<view class="padding flex text-center text-grey bg-white shadow-warp">
				<view class="flex flex-sub flex-direction solid-right">
					<view class="text-xxl text-orange">修改资料</view>
					<view class="margin-top-sm">
						<text class="cuIcon-info"></text> Info
					</view>
				</view>
				<view class="flex flex-sub flex-direction solid-right">
					<view class="text-xxl text-blue">100分</view>
					<view class="margin-top-sm">
						<text class="cuIcon-favor"></text> Point
					</view>
				</view>
				<view class="flex flex-sub flex-direction">
					<view class="text-xxl text-green">普通会员</view>
					<view class="margin-top-sm">
						<text class="cuIcon-crown"></text> Level
					</view>
				</view>
			</view>
			<view class="cu-list menu sm-border card-menu margin-top">
				<view class="cu-item arrow">
					<view class="content">
						<text class="cuIcon-order text-orange"></text>
						<text class="text-grey">出售订单</text>
					</view>
				</view>
				<view class="cu-item arrow">
					<button class="cu-btn content" open-type="contact">
						<text class="cuIcon-order text-orange"></text>
						<text class="text-grey">求购订单</text>
					</button>
				</view>
				<view class="cu-item arrow">
					<button class="cu-btn content" open-type="contact">
						<text class="cuIcon-order text-orange"></text>
						<text class="text-grey">待确认订单</text>
					</button>
				</view>
				<view class="cu-item arrow">
					<button class="cu-btn content" open-type="contact">
						<text class="cuIcon-order text-orange"></text>
						<text class="text-grey">已完成订单</text>
					</button>
				</view>
				<view class="cu-item arrow">
					<navigator class="content" hover-class="none" url="../list/list" open-type="redirect">
						<text class="cuIcon-writefill text-orange"></text>
						<text class="text-grey">意见反馈</text>
					</navigator>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		LOGIN_URL,
		UPDATE_LOGIN_USER_URL
	} from '../../utils/api.js';
	import {
		request
	} from '../../utils/wxRequest.js';
	export default {
		data() {
			return {
				user: null
			}
		},
		methods: {
			wxGetUserInfo: function(res) {
				if (res.detail.rawData) {
					//用户按了允许授权按钮
					this.login(res);
				} else {
					//用户按了拒绝按钮
					uni.showToast({
						title: '获取用户信息失败',
						icon: 'none'
					})
				}
			},
			login: function(e) {
				const self = this;
				let userInfo = e.detail.userInfo;
				console.log(LOGIN_URL);
				// 登录
				uni.login({
					provider: "weixin",
					success: (res) => {
						request(
							LOGIN_URL,
							'POST', {
								code: res.code,
								nickName: userInfo.nickName,
								avatarUrl: userInfo.avatarUrl,
								gender: userInfo.gender
							}
						).then(res => {
							console.log('返回的登录状态为：', res.responseCode)
							if (res.responseCode == '100') {
								uni.setStorageSync('token', res.token);
								uni.setStorageSync('user', res.userInfoDTO);
								uni.setStorageSync('latestLoginTime', new Date());
								console.log('获取的用户信息：', res.userInfoDTO);
								console.log('最近的一次登录时间：', new Date());
								uni.showToast({
									title: '登录成功！'
								});
								self.user = res.userInfoDTO;
							} else {
								uni.showModal({
									title: '错误',
									content: res.responseMsg,
									showCancel: false
								})
							}
						}).catch(error => {
							console.log('error', error);
						});
					}
				});
			}
		},
		created: function() {
			// 组件初始化函数，切底部tab然后再切回去初始化个人中心组件
			let user = uni.getStorageSync('user');
			let latestLoginTime = new Date(uni.getStorageSync('latestLoginTime'));
			if (user) {
				this.user = user;
				let wechatOpenId = this.user.wechatOpenId;
				console.log('该登录用户的信息为：', user);
				// 如果用户在7天后重新登录该小程序的话，则重新发布token给用户，刷新用户的登录有效期
				let nowLoginTime = new Date(); // 当前登录时间
				console.log('该登录当前登录时间为：', nowLoginTime);
				let nextDayLoginTime = latestLoginTime.setTime(latestLoginTime.getTime() + 7 * 24 * 60 * 60 * 1000); // 上一次登录时间的24小时后的时间
				console.log(' 该用户距离此次登录时间的7天后的时间为：', new Date(nextDayLoginTime));
				if (nowLoginTime > new Date(nextDayLoginTime)) {
					console.log('距离上次用户登录已过7天，重置用户的token以延长用户的登录时效');
					uni.request({
						url: UPDATE_LOGIN_USER_URL,
						method: 'POST',
						data: {
							'wechatOpenId': wechatOpenId
						},
						header: {
							'Content-Type': 'application/x-www-form-urlencoded',
							'X-Token': uni.getStorageSync('token').token
						},
						success: (res) => {
							let resData = res.data;
							if (resData.responseCode === '100') {
								uni.setStorageSync('token', resData.token);
								uni.setStorageSync('user', resData.userInfoDTO);
								uni.setStorageSync('latestLoginTime', new Date());
								console.log('获取更新完后用户信息：', resData.userInfoDTO);
								console.log('更新完后的最近一次登录时间：', new Date());
							}
						},
						fail: (res) => {
							uni.showModal({
								title: '错误',
								content: '网络异常',
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
	.user-no-login {
		margin-top: 170upx;
	}

	.img {
		width: 160upx;
		height: 160upx;
		border-radius: 50%;
	}

	.page {
		height: 100vh;
	}

	.user-icon {
		position: absolute;
		z-index: 1000;
		width: 100%;
		height: 600rpx;
		top: 20rpx;
		justify-content: center;
		align-items: center;
		display: flex;
		flex-direction: column;
	}

	.transparent {
		background-color: rgba(255, 255, 255, 0.20) !important;
	}
</style>
