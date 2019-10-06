<template>
	<view class="eticketInfoCard">
		<view class="thumb">
			<image src="../../static/foodImg/startbucks.PNG" class="img" mode="aspectFit"></image>
		</view>
		<view class="detail">
			<view class="mid">
				<view class="title">
					星巴克
				</view>
				<view class="summary">星巴克</div>
				</view>
				<view class="right">
					<view>20积分</view>
					<view class="right">下载</view>
				</view>
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
				z-index: 10;
				max-width: 100%;
				max-height: 100%;
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
				width: 70px;
				text-align: right;
			}
		}
	}
</style>
