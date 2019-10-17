<template>
	<view class="eticketInfoCard">
		<view class="thumb">
			<image src="../../static/foodImg/startbucks.PNG" class="img" mode="aspectFit"></image>
			<view class="tag origin">
				出售
			</view>
		</view>
		<view class="detail">
			<view class="mid">
				<view class="title">
					星巴克大杯券
				</view>
				<view>
					有效期：
				</view>
				<view>
					2019-10-02
				</view>
				<view>
					2019-10-03
				</view>
				<view class="right">
					¥29
				</view>
				<button> 购买</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.eticketInfoCard {

		padding: 15px 15px;
		border-bottom: 1px dashed #ddd;
		display: flex;
		background-color: #fff;
		font-size: 14px;

		.thumb {
			width: 70px;
			height: 70px;
			position: relative;

			.img {
				z-index: 0;
				max-width: 100%;
				max-height: 100%;
			}

			.tag {
				color: #fff;
				padding: 2px 4px;
				position: absolute;
				top: 0;
				left: 0;
			}

			.origin {
				background-color: #ff4444;
			}
		}

		.detail {
			padding-left: 15px;
			word-wrap: break-word;
			width: 100%;
			display: flex;
			flex-direction: row;
			justify-content: space-between;

			.mid {
				.title {
					font-weight: bold;
				}
			}

			.right {
				padding-right: 10upx;
			}
		}
	}
</style>
