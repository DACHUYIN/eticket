<template name="classification">
	<view>
		<view>
			<cu-custom bgColor="bg-gradual-green">
				<block slot="content">
					<text class="text-xl">分类</text>
				</block>
			</cu-custom>
		</view>
		<view class="cu-bar bg-white search SearchStyle">
			<view class="search-form round">
				<text class="cuIcon-search text-xl"></text>
				<input class="text-xl" type="text" placeholder="搜索电子票券" confirm-type="search" @input="searchEticket"></input>
			</view>
		</view>
		<view class="VerticalBox">
			<view class="VerticalNav nav" style="height:100%; width: 30%;">
				<view class="cu-item text-xl" :class="index==tabCur?'text-green cur':''" v-for="(item,index) in list" :key="index"
				 @tap="TabSelect" :data-id="index">
					{{item}}
				</view>
			</view>
			<view class="bg-white" style="height: 100%; width: 70%;margin-left: 30%;">
				<ul v-if="tabCur == '0'">
				  <li>
					  <card></card>
					  <card></card>
					  <card></card>
					  <card></card>
					  <card></card>
					  <card></card>
					  <card></card>
					  <card></card>
				  </li>
				</ul>
				<ul v-if="tabCur == '1'">
				  <li>
					  <card></card>
					  <card></card>
				  </li>
				</ul>
			</view>
		</view>
	</view>
</template>

<script>
    import card from '../../components/card/card.vue'
	export default {
		components: {
			card
		},
		data() {
			return {
				list: ['餐饮券', '影音娱乐券', '景点门票', '电影票', '演唱会门票', '展览门票'],
				tabCur: 0
			}
		},
		methods: {
			searchEticket: function(e) {

			},
			TabSelect: function(e) {
				this.tabCur = e.currentTarget.dataset.id;
			}
		}
	}
</script>

<style>
	.SearchStyle {
		position: fixed;
		width: 100%;
		z-index: 1001;
		border-bottom: 1upx solid #E7EBED;
	}
    
	.VerticalBox {
		display: flex;
		width: 100%;
		height: 90%;
		margin-top: 50px;
	}
	
	.VerticalNav.nav {
		white-space: initial;
		position: fixed;

	}

	.VerticalNav.nav .cu-item {
		width: 100%;
		text-align: left;
		margin: 0;
		border-bottom: 1upx solid #E7EBED;
		height: 44px;
		position: relative;
	}

	.VerticalNav.nav .cu-item.cur {
		background-color: #fff;
	}

	.VerticalNav.nav .cu-item.cur::after {
		content: "";
		width: 8upx;
		height: 30upx;
		border-radius: 10upx 0 0 10upx;
		position: absolute;
		background-color: currentColor;
		top: 0;
		right: 0upx;
		bottom: 0;
		margin: auto;
	}
</style>