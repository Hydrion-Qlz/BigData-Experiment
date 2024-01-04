<template>
  <!--  搜索框  -->
  <div class="query-outer">
    <div class="query-content">
      <!--        logo-->
      <div class="big-logo">Assistance</div>
      <!--        搜索框-->
      <a-input v-model:value="webUrl" class="search" placeholder="please input web url">
        <template #suffix>
          <upload-outlined class="search-btn" style="margin-right: 20px;"/>
          <search-outlined class="search-btn" @click="searchResult"/>
        </template>
      </a-input>
      <!-- 筛选区 -->
      <a-space size="large">
        <div class="options">
          <span class="options-label">主题色:</span>
          <a-input v-model:value="color" type="color" class="color-picker"></a-input>
        </div>
        <div class="options">
          <span class="options-label">分类:</span>
          <a-select v-model:value="category" class="category-selector" allowClear @change="handleCategoryChange"
                    placeholder="category" :listHeight="200" size="small"
                    :options="categoryOptions">
          </a-select>
        </div>
      </a-space>
    </div>
  </div>
</template>

<script>
import {message} from "ant-design-vue";
import {HomeOutlined, SearchOutlined, DownOutlined, UploadOutlined} from '@ant-design/icons-vue'

export default {
  name: "query",
  components: {
    HomeOutlined,
    SearchOutlined,
    DownOutlined,
    UploadOutlined
  },
  data() {
    return {
      webUrl: "",
      color: "#000000",
      category: "category",
      // todo
      categoryOptions: [
        {
          "label": "艺术&娱乐",
          "options": [
            {
              "value": "animation_and_comics",
              "label": "动漫"
            },
            {
              "value": "arts_and_entertainment",
              "label": "其他艺术与娱乐相关"
            },
            {
              "value": "books_and_literature",
              "label": "图书与文学"
            },
            {
              "value": "humor",
              "label": "喜剧幽默"
            },
            {
              "value": "music",
              "label": "音乐"
            },
            {
              "value": "performing_arts",
              "label": "表演艺术"
            },
            {
              "value": "tv_movies_and_streaming",
              "label": "流媒体和在线电视"
            },
            {
              "value": "visual_arts_and_design",
              "label": "视觉艺术与设计"
            }
          ]
        },
        {
          "label": "商业和消费者服务",
          "options": [
            {
              "value": "business_and_consumer_services",
              "label": "其他商业与消费者服务相关"
            },
            {
              "value": "business_services",
              "label": "商业服务"
            },
            {
              "value": "marketing_and_advertising",
              "label": "营销及广告"
            },
            {
              "value": "online_marketing",
              "label": "数字营销"
            },
            {
              "value": "publishing_and_printing",
              "label": "印刷和自我出版"
            },
            {
              "value": "real_estate",
              "label": "房地产"
            },
            {
              "value": "relocation_and_household_moving",
              "label": "转移和搬迁"
            },
            {
              "value": "shipping_and_logistics",
              "label": "运输和物流"
            },
            {
              "value": "textiles",
              "label": "纺织品"
            }
          ]
        },
        {
          "label": "社区与社会",
          "options": [
            {
              "value": "community_and_society",
              "label": "其他社区与社会相关"
            },
            {
              "value": "decease",
              "label": "亡故"
            },
            {
              "value": "faith_and_beliefs",
              "label": "信仰与信念"
            },
            {
              "value": "holidays_and_seasonal_events",
              "label": "假期和季节性活动"
            },
            {
              "value": "lgbtq",
              "label": "LGBTQ"
            },
            {
              "value": "philanthropy",
              "label": "慈善事业"
            },
            {
              "value": "romance_and_relationships",
              "label": "约会和关系"
            }
          ]
        },
        {
          "label": "计算机电子与技术",
          "options": [
            {
              "value": "advertising_networks",
              "label": "广告网络"
            },
            {
              "value": "computers_electronics_and_technology",
              "label": "其他计算机电子技术相关"
            },
            {
              "value": "computer_hardware",
              "label": "计算机硬件"
            },
            {
              "value": "computer_security",
              "label": "计算机安全"
            },
            {
              "value": "consumer_electronics",
              "label": "消费类电子产品"
            },
            {
              "value": "email",
              "label": "电子邮件"
            },
            {
              "value": "file_sharing_and_hosting",
              "label": "文件共享和托管"
            },
            {
              "value": "graphics_multimedia_and_web_design",
              "label": "图形多媒体和网页设计"
            },
            {
              "value": "programming_and_developer_software",
              "label": "编程和开发软件"
            },
            {
              "value": "search_engines",
              "label": "搜索引擎"
            },
            {
              "value": "social_networks_and_online_communities",
              "label": "社交媒体网络"
            },
            {
              "value": "telecommunications",
              "label": "电信"
            },
            {
              "value": "web_hosting_and_domain_names",
              "label": "网页托管和域名"
            }
          ]
        },
        {
          "label": "电子商务和网络购物",
          "options": [
            {
              "value": "auctions",
              "label": "拍卖"
            },
            {
              "value": "classifieds",
              "label": "分类"
            },
            {
              "value": "coupons_and_rebates",
              "label": "优惠券和返现"
            },
            {
              "value": "e-commerce_and_shopping",
              "label": "电子商务和购物-其他"
            },
            {
              "value": "marketplace",
              "label": "在线市场"
            },
            {
              "value": "price_comparison",
              "label": "价格对比"
            },
            {
              "value": "tickets",
              "label": "购票"
            }
          ]
        },
        {
          "label": "金融",
          "options": [
            {
              "value": "accounting_and_auditing",
              "label": "会计和审计"
            },
            {
              "value": "banking_credit_and_lending",
              "label": "银行信贷"
            },
            {
              "value": "finance",
              "label": "其他金融相关"
            },
            {
              "value": "financial_planning_and_management",
              "label": "财务规划与管理"
            },
            {
              "value": "insurance",
              "label": "保险"
            },
            {
              "value": "investing",
              "label": "投资"
            }
          ]
        },
        {
          "label": "餐饮",
          "options": [
            {
              "value": "beverages",
              "label": "饮料"
            },
            {
              "value": "cooking_and_recipes",
              "label": "烹饪和食谱"
            },
            {
              "value": "food_and_drink",
              "label": "其他食品与饮料相关"
            },
            {
              "value": "groceries",
              "label": "食品杂货"
            },
            {
              "value": "restaurants_and_delivery",
              "label": "餐厅和外卖"
            },
            {
              "value": "vegetarian_and_vegan",
              "label": "素食与纯素"
            }
          ]
        },
        {
          "label": "博彩",
          "options": [
            {
              "value": "bingo",
              "label": "宾果"
            },
            {
              "value": "casinos",
              "label": "赌场"
            },
            {
              "value": "gambling",
              "label": "其他赌博相关"
            },
            {
              "value": "lottery",
              "label": "彩票"
            },
            {
              "value": "poker",
              "label": "扑克"
            },
            {
              "value": "sports_betting",
              "label": "体育博彩"
            }
          ]
        },
        {
          "label": "游戏",
          "options": [
            {
              "value": "board_and_card_games",
              "label": "棋盘和纸牌游戏"
            },
            {
              "value": "games",
              "label": "其他游戏相关"
            },
            {
              "value": "puzzles_and_brainteasers",
              "label": "拼图和脑筋急转弯"
            },
            {
              "value": "roleplaying_games",
              "label": "角色扮演游戏"
            },
            {
              "value": "video_games_consoles_and_accessories",
              "label": "电子游戏机和配件"
            }
          ]
        },
        {
          "label": "健康",
          "options": [
            {
              "value": "addictions",
              "label": "瘾症"
            },
            {
              "value": "alternative_and_natural_medicine",
              "label": "替代医学及自然疗法"
            },
            {
              "value": "biotechnology_and_pharmaceuticals",
              "label": "生物科技及制药"
            },
            {
              "value": "childrens_health",
              "label": "儿童健康"
            },
            {
              "value": "dentist_and_dental_services",
              "label": "牙医和牙科服务"
            },
            {
              "value": "developmental_and_physical_disabilities",
              "label": "发育和生理障碍"
            },
            {
              "value": "geriatric_and_aging_care",
              "label": "老年护理"
            },
            {
              "value": "health",
              "label": "其他健康相关"
            },
            {
              "value": "health_conditions_and_concerns",
              "label": "健康状况和问题"
            },
            {
              "value": "medicine",
              "label": "医药"
            },
            {
              "value": "mens_health",
              "label": "男性健康"
            },
            {
              "value": "mental_health",
              "label": "精神健康"
            },
            {
              "value": "nutrition_diets_and_fitness",
              "label": "营养饮食和健身"
            },
            {
              "value": "pharmacy",
              "label": "药房"
            },
            {
              "value": "public_health_and_safety",
              "label": "公共卫生与安全"
            },
            {
              "value": "womens_health",
              "label": "女性健康"
            }
          ]
        },
        {
          "label": "重工业与工程",
          "options": [
            {
              "value": "aerospace_and_defense",
              "label": "航空航天与国防"
            },
            {
              "value": "agriculture",
              "label": "农业"
            },
            {
              "value": "architecture",
              "label": "建筑"
            },
            {
              "value": "chemical_industry",
              "label": "化学工业"
            },
            {
              "value": "construction_and_maintenance",
              "label": "建造及维修"
            },
            {
              "value": "energy_industry",
              "label": "能源行业"
            },
            {
              "value": "heavy_industry_and_engineering",
              "label": "其他重工业与工程相关"
            },
            {
              "value": "metals_and_mining",
              "label": "金属和采矿业"
            },
            {
              "value": "waste_water_and_environmental",
              "label": "废水和环境"
            }
          ]
        },
        {
          "label": "爱好和休闲",
          "options": [
            {
              "value": "ancestry_and_genealogy",
              "label": "祖先和家谱"
            },
            {
              "value": "antiques_and_collectibles",
              "label": "古董和收藏品"
            },
            {
              "value": "camping_scouting_and_outdoors",
              "label": "露营童子军和户外活动"
            },
            {
              "value": "crafts",
              "label": "工艺品"
            },
            {
              "value": "hobbies_and_leisure",
              "label": "其他爱好与休闲相关"
            },
            {
              "value": "models",
              "label": "模型"
            },
            {
              "value": "photography",
              "label": "摄影"
            }
          ]
        },
        {
          "label": "居家与花园",
          "options": [
            {
              "value": "furniture",
              "label": "家具"
            },
            {
              "value": "gardening",
              "label": "园艺"
            },
            {
              "value": "home_and_garden",
              "label": "其他家居与花园相关"
            },
            {
              "value": "home_improvement_and_maintenance",
              "label": "家居装修及保养"
            },
            {
              "value": "interior_design",
              "label": "室内设计"
            }
          ]
        },
        {
          "label": "工作和职业",
          "options": [
            {
              "value": "human_resources",
              "label": "人力资源"
            },
            {
              "value": "jobs_and_career",
              "label": "其他工作与职业相关"
            },
            {
              "value": "jobs_and_employment",
              "label": "工作和就业"
            }
          ]
        },
        {
          "label": "法律与政府",
          "options": [
            {
              "value": "government",
              "label": "政府"
            },
            {
              "value": "immigration_and_visas",
              "label": "移民和签证"
            },
            {
              "value": "law_and_government",
              "label": "其他法律与政府相关"
            },
            {
              "value": "law_enforcement_and_protective_services",
              "label": "执法和保护服务"
            },
            {
              "value": "legal",
              "label": "法律"
            },
            {
              "value": "national_security",
              "label": "国家安全"
            }
          ]
        },
        {
          "label": "生活方式",
          "options": [
            {
              "value": "beauty_and_cosmetics",
              "label": "美容化妆品"
            },
            {
              "value": "childcare",
              "label": "育儿"
            },
            {
              "value": "fashion_and_apparel",
              "label": "时装服饰"
            },
            {
              "value": "gifts_and_flowers",
              "label": "礼物和鲜花"
            },
            {
              "value": "jewelry_and_luxury_products",
              "label": "珠宝和奢侈品"
            },
            {
              "value": "lifestyle",
              "label": "其他生活方式相关"
            },
            {
              "value": "tobacco",
              "label": "烟草"
            },
            {
              "value": "weddings",
              "label": "婚礼"
            }
          ]
        },
        {
          "label": "新闻和媒体发布商",
          "options": []
        },
        {
          "label": "宠物与动物",
          "options": [
            {
              "value": "animals",
              "label": "动物"
            },
            {
              "value": "birds",
              "label": "鸟类"
            },
            {
              "value": "fish_and_aquaria",
              "label": "鱼类和水族"
            },
            {
              "value": "horses",
              "label": "马匹"
            },
            {
              "value": "pets",
              "label": "宠物"
            },
            {
              "value": "pets_and_animals",
              "label": "其他宠物与动物相关"
            },
            {
              "value": "pet_food_and_supplies",
              "label": "宠物食品和用品"
            }
          ]
        },
        {
          "label": "参考文献",
          "options": [
            {
              "value": "dictionaries_and_encyclopedias",
              "label": "字典和百科全书"
            },
            {
              "value": "maps",
              "label": "地图"
            },
            {
              "value": "public_records_and_directories",
              "label": "公共记录和名录"
            },
            {
              "value": "reference_materials",
              "label": "其他参考材料相关"
            }
          ]
        },
        {
          "label": "科学与教育",
          "options": [
            {
              "value": "astronomy",
              "label": "天文学"
            },
            {
              "value": "biology",
              "label": "生物学"
            },
            {
              "value": "business_training",
              "label": "业务培训"
            },
            {
              "value": "chemistry",
              "label": "化学"
            },
            {
              "value": "earth_sciences",
              "label": "地球科学"
            },
            {
              "value": "education",
              "label": "教育"
            },
            {
              "value": "environmental_science",
              "label": "环境科学"
            },
            {
              "value": "grants_scholarships_and_financial_aid",
              "label": "经费奖学金和经济援助"
            },
            {
              "value": "history",
              "label": "历史记录"
            },
            {
              "value": "libraries_and_museums",
              "label": "图书馆和博物馆"
            },
            {
              "value": "literature",
              "label": "文学"
            },
            {
              "value": "math",
              "label": "数学"
            },
            {
              "value": "philosophy",
              "label": "哲学"
            },
            {
              "value": "physics",
              "label": "物理"
            },
            {
              "value": "public_records_and_directories",
              "label": "公共记录和名录"
            },
            {
              "value": "science_and_education",
              "label": "其他科学与教育相关"
            },
            {
              "value": "social_sciences",
              "label": "社会科学"
            },
            {
              "value": "universities_and_colleges",
              "label": "大学和学院"
            },
            {
              "value": "weather",
              "label": "天气"
            }
          ]
        },
        {
          "label": "体育",
          "options": [
            {
              "value": "american_football",
              "label": "美式足球"
            },
            {
              "value": "baseball",
              "label": "棒球"
            },
            {
              "value": "basketball",
              "label": "篮球"
            },
            {
              "value": "boxing",
              "label": "拳击"
            },
            {
              "value": "climbing",
              "label": "登山"
            },
            {
              "value": "cycling_and_biking",
              "label": "自行车及自行车运动"
            },
            {
              "value": "extreme_sports",
              "label": "极限运动"
            },
            {
              "value": "fantasy_sports",
              "label": "经营模拟游戏"
            },
            {
              "value": "fishing",
              "label": "钓鱼"
            },
            {
              "value": "golf",
              "label": "高尔夫"
            },
            {
              "value": "hunting_and_shooting",
              "label": "狩猎和射击"
            },
            {
              "value": "martial_arts",
              "label": "武术"
            },
            {
              "value": "rugby",
              "label": "英式橄榄球"
            },
            {
              "value": "running",
              "label": "跑步"
            },
            {
              "value": "soccer",
              "label": "足球"
            },
            {
              "value": "sports",
              "label": "其他体育相关"
            },
            {
              "value": "tennis",
              "label": "网球"
            },
            {
              "value": "volleyball",
              "label": "排球"
            },
            {
              "value": "water_sports",
              "label": "水上运动"
            },
            {
              "value": "winter_sports",
              "label": "冬季运动"
            }
          ]
        },
        {
          "label": "旅行和旅游",
          "options": [
            {
              "value": "accommodation_and_hotels",
              "label": "住宿及酒店"
            },
            {
              "value": "air_travel",
              "label": "空中交通"
            },
            {
              "value": "car_rentals",
              "label": "租车"
            },
            {
              "value": "ground_transportation",
              "label": "地面交通"
            },
            {
              "value": "tourist_attractions",
              "label": "旅游景点"
            },
            {
              "value": "transportation_and_excursions",
              "label": "交通和短途旅行"
            },
            {
              "value": "travel_and_tourism",
              "label": "其他旅游与观光相关"
            }
          ]
        },
        {
          "label": "车辆",
          "options": [
            {
              "value": "automotive_industry",
              "label": "汽车行业"
            },
            {
              "value": "aviation",
              "label": "航空"
            },
            {
              "value": "boats",
              "label": "船只"
            },
            {
              "value": "makes_and_models",
              "label": "品牌和型号"
            },
            {
              "value": "motorcycles",
              "label": "摩托车"
            },
            {
              "value": "motorsports",
              "label": "赛车"
            },
            {
              "value": "vehicles",
              "label": "其他交通工具相关"
            }
          ]
        }
      ]
    }
  },
  methods: {
    handleCategoryChange(data) {
      console.log(data)
    },
    searchResult(data) {
      if (this.webUrl === '') {
        message.error("请输入网页链接")
      } else if (this.category === 'category') {
        message.error("请选择分类信息")
      } else {
        message.success("成功")
        console.log(this.webUrl)
        console.log(this.color)
        console.log(this.category)
        this.$router.push({
          name: "detail",
          query: {
            url: this.webUrl,
            color: this.color,
            category: this.category
          }
        })
      }
    }
  },
  async created() {
    //todo:设置种类
  }
}
</script>

<style scoped>

.query-outer {
  position: relative;
  width: 100%;
  height: calc(100% - 55px);
}

.query-content {
  width: 550px;
  position: absolute;
  left: 50%;
  top: 45%;
  transform: translate(-50%, -65%);
  text-align: center;

  /*box-shadow: 1px 0px 10px 1px rgba(30, 30, 30, .3);*/
  /*padding-top: 60px;*/
  /*padding-bottom: 30px;*/
}


.big-logo {
  height: 80px;
  line-height: 80px;
  color: #fff;
  font-weight: bold;
  font-size: 70px;
  margin-bottom: 60px;
}

.search {
  width: 500px;
  height: 35px;
  /*transform: translateY(-20px);*/
  margin-bottom: 20px;
  border-radius: 5px;
}

.search-btn {
  color: rgba(0, 0, 0, 0.5)
}

.search-btn:hover, .search-btn:active {
  color: #2196f3;
  cursor: pointer;
}

.options .options-label {
  color: #fff;
  font-size: 16px;
}

.color-picker, .category-selector {
  margin-left: 10px;
  height: 24px;
  vertical-align: top;
}

.color-picker {
  width: 100px;
  cursor: pointer;
}

.category-selector {
  width: 150px;
}
</style>