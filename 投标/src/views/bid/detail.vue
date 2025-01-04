<template>
  <div class="detail">
    <el-breadcrumb separator="/" class="breadcrumb">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/bid/list">公告列表页</a></el-breadcrumb-item>
      <el-breadcrumb-item>公告详情页</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="content">
      <div class="title"> {{ bidTitle }}</div>
      <div class="publish">
        <span style="color: #333333;">发布企业：</span><span style="color: #666666;margin-right: 34px">{{
          bidPublisher
        }}</span>
        <span style="color: #333333">发布时间：</span><span style="color: #666666">{{ bidPublishDatetime }}</span>
        <!--        {{-->
        <!--          parseTime(detailData.bidPublishDatetime, '{y}年{m}月{d}日')-->
        <!--        }}-->
      </div>
      <div class="bidContent" ref="bidContent"/>
      <div class="download">
        <ul>
          <template v-for="item in bidFiles">
            <li @click="downloadFile(item.fileName,item.filePath)">
              <span><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="none"
                         version="1.1" width="60" height="22" viewBox="0 0 60 22"><g style="mix-blend-mode:passthrough"><path
                d="M22.3398,13.0063Q22.7227,15.6268,21,15.9777Q20.4258,16.0734,19.6738,15.9914Q19.5645,15.5493,19.373,15.1528L19.373,18.6164L18.3477,18.6164L18.3477,5.49137L22.3535,5.49137L22.3535,6.36637Q21.8477,8.11637,21.123,10.2173Q22.1758,11.7622,22.3398,13.0063ZM25.0742,7.82471L28.5332,7.82471L28.5332,4.90804L29.5996,4.90804L29.5996,7.82471L31.3086,7.82471L31.3086,8.99137L29.5996,8.99137L29.5996,16.4699Q29.5996,18.3247,27.6445,18.3247Q26.8379,18.3247,26.0039,18.2974Q25.9355,17.8052,25.7852,17.158Q26.8105,17.158,27.5078,17.158Q28.5332,17.158,28.5332,16.2056L28.5332,8.99137L25.0742,8.99137L25.0742,7.82471ZM21.9434,10.5181Q23.4336,8.21663,24.3633,4.90804L25.4297,5.14046Q25.0423,6.43473,24.5684,7.59684L24.5684,18.6164L23.5293,18.6164L23.5293,9.84359Q23.0508,10.7231,22.5176,11.5024Q22.2715,10.9692,21.9434,10.5181ZM20.6309,15.1164Q21.5605,14.9295,21.2598,13.0793Q21.0684,11.9263,20.0293,10.5135Q20.6172,8.71794,21.2324,6.36637L19.373,6.36637L19.373,15.1164Q20.0977,15.1847,20.6309,15.1164ZM25.3066,10.5682L26.168,10.158Q26.9883,11.5936,27.6582,13.2069L26.6465,13.658Q26.0039,12.0174,25.3066,10.5682ZM35.7383,12.4914L40.4824,12.4914L40.4824,8.99137L37.8802,8.99137Q37.3242,10.4588,36.668,11.6164Q36.2715,11.2336,35.7656,10.8918Q37.0645,8.604,37.6797,5.78304L38.7871,6.09749Q38.5228,7.1639,38.2129,8.11637L40.4824,8.11637L40.4824,4.90804L41.5762,4.90804L41.5762,8.11637L44.8438,8.11637L44.8438,8.99137L41.5762,8.99137L41.5762,12.4914L45.2949,12.4914L45.2949,13.3664L41.5762,13.3664L41.5762,18.3247L40.4824,18.3247L40.4824,13.3664L35.7383,13.3664L35.7383,12.4914ZM31.623,11.6164Q33.5098,8.84554,34.6582,4.90804L35.7793,5.23617Q35.2734,6.84033,34.6855,8.24398L34.6855,18.3247L33.6055,18.3247L33.6055,10.436Q32.9082,11.7166,32.1152,12.783Q31.9102,12.2043,31.623,11.6164ZM52.4909,8.69971Q52.8509,8.69971,53.1107,8.95492Q53.3704,9.21012,53.3704,9.57015Q53.3704,9.93018,53.1152,10.1899Q52.8646,10.4497,52.4909,10.4497Q52.1126,10.4497,51.8574,10.1899Q51.6068,9.93018,51.6068,9.57015Q51.6068,9.21012,51.8665,8.95492Q52.1263,8.69971,52.4909,8.69971ZM52.4909,14.8247Q52.8509,14.8247,53.1107,15.0799Q53.3704,15.3351,53.3704,15.6997Q53.3704,16.0734,53.1107,16.3241Q52.8509,16.5747,52.4909,16.5747Q52.1263,16.5747,51.8665,16.3241Q51.6068,16.0734,51.6068,15.6997Q51.6068,15.3351,51.8665,15.0799Q52.1263,14.8247,52.4909,14.8247Z"
                fill="#333333" fill-opacity="1"/></g></svg></span>
              <span>附件：</span>
              <span><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="none"
                         version="1.1" width="20" height="20" viewBox="0 0 20 20"><defs><clipPath
                id="master_svg0_269_00824"><rect x="0" y="0" width="20" height="20" rx="0"/></clipPath></defs><g
                clip-path="url(#master_svg0_269_00824)"><g><path
                d="M11.325389625,5.273828125L16.724140625,5.273828125C17.096930625,5.273828125,17.399140625,4.971618125,17.399140625,4.598828125C17.399140625,4.226036125,17.096930625,3.923828125,16.724140625,3.923828125L11.325389625,3.923828125C10.952597625,3.923828125,10.650390625,4.226036125,10.650390625,4.598828125C10.650390625,4.971618125,10.952597625,5.273828125,11.325389625,5.273828125Z"
                fill="#565656" fill-opacity="1"/></g><g><path
                d="M16.7238,5.9990234375L9.7375,5.9990234375C9.61256,5.5946534375,9.50331,5.1855934375,9.41,4.7727734375C9.065000000000001,3.3427734375,8.8925,2.6240234375,8.2025,2.6240234375L3.22375,2.6240234375C2.135,2.6240234375,1.25,3.6615234375,1.25,4.937773437500001L1.25,15.0615234375C1.25,16.3377234375,2.135,17.3753234375,3.22375,17.3753234375L16.7238,17.3753234375C17.8125,17.3753234375,18.6988,16.3377234375,18.6988,15.0603234375L18.6988,8.310273437500001C18.6988,7.0365234375,17.8125,5.9990234375,16.7238,5.9990234375ZM17.4488,15.0615234375C17.4488,15.6378234375,17.116300000000003,16.126523437499998,16.7238,16.126523437499998L3.22375,16.126523437499998C2.83125,16.1253234375,2.5,15.6378234375,2.5,15.0615234375L2.5,4.937773437500001C2.5,4.3615234375,2.83125,3.8740234375,3.22375,3.8740234375L7.8775,3.8740234375C7.9775,4.1677734375,8.10375,4.6902734375,8.19375,5.0652734375C8.54375,6.5190234375,8.719999999999999,7.2490234375,9.4175,7.2490234375L16.7225,7.2490234375C17.115000000000002,7.2490234375,17.4475,7.7352734375,17.4475,8.3115234375L17.4475,15.0615234375L17.4488,15.0615234375Z"
                fill="#565656" fill-opacity="1"/></g></g></svg></span>
              <span>{{ item.fileName }}</span>

            </li>

          </template>
        </ul>
      </div>
    </div>
  </div>

</template>

<script>
import {getBidding} from '@/api/bid/bidding'
import {parseTime} from "../../utils/neu";
import axios from 'axios'

export default {
  name: "detail",
  methods: {
    downloadFile(fileName, filePath) {
      axios({
        method: 'get',
        url: filePath,
        responseType: 'blob',
        headers: {
          'data-type': 'Buffer',
          'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
        }
      }).then(res => {
        const url = window.URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', fileName) // 指定下载后的文件名，防跳转
        document.body.appendChild(link)
        link.click()
      })

    }
  },
  data() {
    return {
      id: "",
      detailData: null,
      bidTitle: "",
      bidPublisher: "",
      bidPublishDatetime: "",
      bidContent: "",
      bidFiles: []
    }
  },
  async created() {
    const that = this;
    that.id = that.$route.query.id
  },
  mounted() {
    const that = this;
    getBidding(that.id).then(response => {
      that.$nextTick(() => {
        that.detailData = response.data
        that.bidTitle = that.detailData.bidTitle;
        that.bidPublisher = that.detailData.bidPublisher;
        that.bidPublishDatetime = that.parseTime(that.detailData.bidPublishDatetime, '{y}年{m}月{d}日');
        that.bidContent = that.detailData.bidContent;
        that.bidFiles = that.detailData.filesList;
        this.$refs.bidContent.innerHTML = that.bidContent
        this.$forceUpdate()
      })
    });
  },

}
</script>

<style lang="scss" scoped>
.detail {
  .breadcrumb {
    height: 21px;
    font-family: MicrosoftYaHei, sans-serif;
    font-size: 14px;
    font-weight: normal;
    line-height: 21px;
    letter-spacing: 0em;
    margin-left: 30px
  }

  .content {
    margin: 14px 30px 44px 30px;
    background: #FFFFFF;
    border-radius: 10px;
    opacity: 1;
    width: 1380px;
    min-height: 860px;
    display: flex;
    flex-direction: column;

    .title {
      font-family: MicrosoftYaHei, sans-serif;
      height: 35px;
      opacity: 1;
      font-size: 24px;
      font-weight: bold;
      text-align: center;
      letter-spacing: 0em;
      color: #3D3D3D;
      margin-top: 24px;
    }

    .publish {
      font-family: MicrosoftYaHei, sans-serif;
      font-size: 14px;
      font-weight: normal;
      line-height: 22px;
      letter-spacing: 0em;
      color: #333333;
      margin-top: 30px;

      &:nth-child(n + 1) {
        font-family: MicrosoftYaHei, sans-serif;
        font-size: 14px;
        font-weight: normal;
        line-height: 22px;
        letter-spacing: 0em;
        /* 次强调正文标题 */
        color: #333333;
      }

      &:nth-child(n + 2) {
        font-family: MicrosoftYaHei, sans-serif;
        font-size: 14px;
        font-weight: normal;
        line-height: 22px;
        letter-spacing: 0em;
        /* 次强调正文标题 */
        color: #666666;
      }
    }
  }

  .bidContent {
    min-height: 575.34px;
    text-align: left;
    margin: 20px 44px 0 36px;
  }

  .download {
    display: flex;
    margin: 30px 0px 30px 46px;

    ul {
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;

      li {
        margin: 20px 0 0 20px;
        list-style: none;
        font-family: MicrosoftYaHei, serif;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-family: MicrosoftYaHei, serif;
        font-size: 14px;
        font-weight: normal;
        letter-spacing: 0em;

        color: #3D3D3D;

        .content {
          width: 540px;
          display: flex;
          justify-content: flex-start;
          align-items: center;
        }

        .datetime {

        }
      }

      li:hover {
        color: #13B69F;
        cursor: pointer;
      }
    }
  }

}

</style>
