<template name="classification">
	<view>
		<view>
			<cu-custom bgColor="bg-gradual-green">
				<block slot="content">
					<text class="text-xl">分类</text>
				</block>
			</cu-custom>
		</view>
		<view class="cu-bar bg-white search SearchStyle" >
			<view class="search-form round">
				<text class="cuIcon-search text-xl"></text>
				<input class="text-xl" type="text" placeholder="搜索电子票券" confirm-type="search" @input="searchEticket"></input>
			</view>
		</view>
		<view class="VerticalBox">
			<scroll-view class="VerticalNav nav" scroll-y scroll-with-animation :scroll-top="verticalNavTop" style="height:calc(100vh - 375upx);width: 30%;">
				<view class="cu-item text-xl" :class="index==tabCur?'text-green cur':''" v-for="(item,index) in list" :key="index"
				 @tap="TabSelect" :data-id="index">
					{{item}}
				</view>
			</scroll-view>
			<scroll-view class="bg-white" style="width: 70%;">
				
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: ['餐饮券', '影音娱乐券', '景点门票', '电影票', '演唱会门票', '展览门票'],
				tabCur: 0,
				mainCur: 0,
				verticalNavTop: 0,
			}
		},
		methods: {
			searchEticket: function(e) {

			},
			TabSelect: function(e) {
				this.tabCur = e.currentTarget.dataset.id;
				this.mainCur = e.currentTarget.dataset.id;
				this.verticalNavTop = (e.currentTarget.dataset.id - 1) * 50
			},
			VerticalMain: function(e) {
				let that = this;
				let tabHeight = 0;
				if (this.load) {
					for (let i = 0; i < this.list.length; i++) {
						let view = uni.createSelectorQuery().select("#main-" + this.list[i].id);
						view.fields({
							size: true
						}, data => {
							this.list[i].top = tabHeight;
							tabHeight = tabHeight + data.height;
							this.list[i].bottom = tabHeight;
						}).exec();
					}
					this.load = false
				}
				let scrollTop = e.detail.scrollTop + 10;
				for (let i = 0; i < this.list.length; i++) {
					if (scrollTop > this.list[i].top && scrollTop < this.list[i].bottom) {
						this.verticalNavTop = (this.list[i].id - 1) * 50
						this.tabCur = this.list[i].id
						console.log(scrollTop)
						return false
					}
				}
			}
		},
		created: function() {
			/* this.list = list;
			this.listCur = list[0]; */
		}
	}
</script>

<style>
	.SearchStyle {
		background-color: #ffffff;
		display: flex;
		align-items: center;
		border-bottom: 1upx solid #E7EBED;
	}
    
	.VerticalNav.nav {
		white-space: initial;
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

	.VerticalBox {
		display: flex;
	}

	.VerticalMain {
		background-color: #f1f1f1;
		flex: 1;
	}
</style>
