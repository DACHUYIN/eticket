<template>
	<view>
		<homepage v-if="pageCur=='homepage'"></homepage>
		<classification v-else-if="pageCur=='classification'"></classification>
		<message v-else-if="pageCur=='message'"></message>
		<personalcenter v-else="pageCur=='personalcenter'"></personalcenter>
		<view class="cu-bar tabbar bg-white shadow foot">
			<view class="action" :class="pageCur=='homepage'?'text-green':'text-gray'" @click="NavChange" data-cur="homepage">
				<view class="cuIcon-home"></view>首页
			</view>
			<view class="action" :class="pageCur=='classification'?'text-green':'text-gray'" @click="NavChange" data-cur="classification">
				<view class="cuIcon-similar"></view>分类
			</view>
			<view class="action text-gray add-action">
				<button class="cu-btn cuIcon-add bg-green shadow" @click="CommitEticket"></button>发布
			</view>
			<view class="action" :class="pageCur=='message'?'text-green':'text-gray'" @click="NavChange" data-cur="message">
				<view class="cuIcon-new"></view>消息
			</view>
			<view class="action" :class="pageCur=='personalcenter'?'text-green':'text-gray'" @click="NavChange" data-cur="personalcenter">
				<view class="cuIcon-my"></view>我的
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				pageCur: 'homepage',
			}
		},
		methods: {
			NavChange: function(e) {
				this.pageCur = e.currentTarget.dataset.cur;
			},
			CommitEticket: function(e) {
				uni.navigateTo({
					url: '../commiteticket/commiteticket'
				})
			}
		}
	}
</script>

<style>

</style>
