<template>
  <div class="information">
    <div class="content">
      <el-row>
        <el-col :span="8">
          <span class="label">企业名称：</span>
          <span class="text">{{ enterpriseData.name }}</span>
        </el-col>
        <el-col :span="16">
          <span class="label">统一社会信用代码：</span>
          <span class="text">{{ enterpriseData.usci }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">公司注册地址：</span>
          <span class="text">{{ enterpriseData.address }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="label">机构类型：</span>
          <span class="text">{{ getDictValue(enterpriseData.affiliation, 'enterpriseAffiliation') }}</span>
        </el-col>
        <el-col :span="8">
          <span class="label">企业性质：</span>
          <span class="text">{{  getDictValue(enterpriseData.enterpriseNature, 'enterpriseNature') }}</span>
        </el-col>
        <el-col :span="8">
          <span class="label">成立日期：</span>
          <span class="text">{{ parseTime(enterpriseData.doe, '{y}年{m}月{d}日') }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="label">法人/负责人：</span>
          <span class="text">{{ enterpriseData.corpn }}</span>
        </el-col>
        <el-col :span="16">
          <span class="label">法人/负责人身份证：</span>
          <span class="text">{{ enterpriseData.corpnIdcard }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">法人/负责人身份证扫描件：</span>
          <span class="text"><el-button type="text" style="color: #17C2AA;padding-top: 0"
                                        @click="showImage(enterpriseData.corpnIdcardImage,'法人/负责人身份证扫描件')">查看图片</el-button></span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">单位联系地址：</span>
          <span class="text">{{ enterpriseData.contactAddress }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="label">经营范围：</span>
          <span class="text">{{ enterpriseData.businessScope }}</span>
        </el-col>
        <el-col :span="16">
          <span class="label">公司简介：</span>
          <span class="text">{{ enterpriseData.profile }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">营业执照有效期：</span>
          <span class="text">{{ enterpriseData.businessLicenseGp }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">营业执照扫描件：</span>
          <span class="text"><el-button type="text" style="color: #17C2AA;padding-top: 0"
                                        @click="showImage(enterpriseData.businessLicenseImage,'营业执照扫描件')">查看图片</el-button></span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="label">开户行：</span>
          <span class="text">{{ enterpriseData.bank }}</span>
        </el-col>
        <el-col :span="16">
          <span class="label">银行账号：</span>
          <span class="text">{{ enterpriseData.account }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">开户行地址：</span>
          <span class="text">{{ enterpriseData.addr }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="label">单位注册电话：</span>
          <span class="text">{{ enterpriseData.registrationPhone }}</span>
        </el-col>
        <el-col :span="16">
          <span class="label">单位注册地址：</span>
          <span class="text">{{ enterpriseData.contactAddr }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="label">注册资本（万元）：</span>
          <span class="text">{{ enterpriseData.registeredCapital }}</span>
        </el-col>
        <el-col :span="16">
          <span class="label">实缴资本：</span>
          <span class="text">{{ enterpriseData.paidinCapital }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <span class="label">开户许可证/基本户存款信息证：</span>
          <span class="text"><el-button type="text" style="color: #17C2AA;padding-top: 0"
                                        @click="showImage(enterpriseData.accountLicence,'开户许可证/基本户存款信息证')">查看图片</el-button></span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="width: 1200px;display: flex;flex-direction: row-reverse;">
            <el-button style="background: #17C2AA;width: 72px;height: 32px;border-radius: 4px;color: #FFFFFF;"
                       @click="handleUpdate">修改
            </el-button>
          </div>
        </el-col>
      </el-row>

    </div>
    <el-dialog :visible.sync="imageVisible" :title="`${imageVisibleTitle}`" width="800px" append-to-body
               :closed="closeImage">
      <el-image :src="imageUrl"
                style="display: block; max-width: 100%; min-height: 300px; margin: 0 auto;">
        <div slot="placeholder" class="image-slot">
          加载中<span class="dot">...</span>
        </div>
      </el-image>
    </el-dialog>
    <el-dialog title="修改企业信息" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="enterpriseData" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司注册地址" prop="address" style="width: 100%">
              <el-input v-model="enterpriseData.address" placeholder="请输入公司注册地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属机构" prop="affiliation" style="width: 100%">
              <!--          <el-input v-model="form.affiliation" placeholder="请输入所属机构"/>-->
              <el-select v-model="enterpriseData.affiliation" placeholder="请输入所属机构">
                <el-option
                  v-for="dict in enterpriseAffiliation"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="企业性质" prop="enterpriseNature">
              <!--          <el-input v-model="form.enterpriseNature" placeholder="请输入企业性质"/>-->
              <el-select v-model="enterpriseData.enterpriseNature" placeholder="请输入企业性质">
                <el-option
                  v-for="dict in enterpriseNature"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成立日期" prop="doe">
              <el-date-picker clearable size="small"
                              v-model="enterpriseData.doe"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择成立日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法人/负责人" prop="corpn" style="width: 100%">
              <el-input v-model="enterpriseData.corpn" placeholder="请输入法人/负责人"/>
            </el-form-item>

          </el-col>
          <el-col :span="12">
            <el-form-item label="法人/负责人身份证" prop="corpnIdcard" style="width: 100%">
              <el-input v-model="enterpriseData.corpnIdcard" placeholder="请输入法人/负责人身份证"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="单位联系地址" prop="contactAddress" style="width: 100%">
              <el-input v-model="enterpriseData.contactAddress" placeholder="请输入联系地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经营范围" prop="businessScope" style="width: 100%">
              <el-input v-model="enterpriseData.businessScope" placeholder="请输入经营范围"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">

            <el-form-item label="营业执照扫描件" prop="businessLicenseImage" v-if="enterpriseData.id !== null">
              <fileUpload :file-type="fileType" :is-show-file-list="false" :is-show-tip="false" v-model="enterpriseData.businessLicenseImage"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司简介" prop="profile" style="width: 100%">
              <el-input v-model="enterpriseData.profile" placeholder="请输入经营范围"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="营业执照有效期" prop="businessLicenseGp" style="width: 100%">
              <el-input v-model="enterpriseData.businessLicenseGp" placeholder="请输入营业执照有效期"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="统一社会信用代码" prop="usci" style="width: 100%">
              <el-input v-model="enterpriseData.usci" placeholder="请输入统一社会信用代码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法人/负责人身份证扫描件" prop="corpnIdcardImage">
              <template slot="label">
                <div style="width: 230px"><span style="color: red">*</span>法人/负责人身份证扫描件</div>
              </template>
              <fileUpload :file-type="fileType" :is-show-file-list="false" :is-show-tip="false" v-model="enterpriseData.corpnIdcardImage"
                          style="margin-left: 100px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">

          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户银行" prop="bank" style="width: 100%">
              <el-input v-model="enterpriseData.bank" placeholder="请输入开户银行"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="银行账号" prop="account" style="width: 100%">
              <el-input v-model="enterpriseData.account" placeholder="请输入银行账号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行地址" prop="addr">
              <el-input v-model="enterpriseData.addr" placeholder="请输入开户行地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册电话" prop="registrationPhone" style="width: 100%">
              <el-input v-model="enterpriseData.registrationPhone" placeholder="请输入注册电话"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="注册地址" prop="contactAddr" style="width: 100%">
              <el-input v-model="enterpriseData.contactAddr" placeholder="请输入注册地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="注册资本（万元）" prop="registeredCapital" style="width: 100%">
              <el-input v-model="enterpriseData.registeredCapital" placeholder="请输入注册资本"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="实缴资本（万元）" prop="paidinCapital" style="width: 100%">
              <el-input v-model="enterpriseData.paidinCapital" placeholder="请输入实缴资本"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">

          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="" prop="accountLicence" style="width: 100%">
              <template slot="label">
                <div style="width: 200px"><span style="color: red">*</span>开户许可证/基本户存款信息证</div>
              </template>
              <fileUpload :file-type="fileType" :is-show-file-list="false" :is-show-tip="false" v-model="enterpriseData.accountLicence"
                          style="margin-left: 100px"/>
              <!--              <el-input v-model="enterpriseData.accountLicence" placeholder="请输入开户许可证/基本户存款信息证"/>-->
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addEnterprise, getEnterprise, updateEnterprise} from '@/api/bid/enterprise'
import {parseTime} from "@/utils/neu";
import {getBaseUrl} from '@/utils/env'
import ImageUpload from '@/components/ImageUpload'
import FileUpload from '@/components/FileUpload'
import {rules} from './enterpriseInfoData'
import {mapGetters} from "vuex";

export default {
  name: "enterpriseInfo",
  components: {
    ImageUpload,
    FileUpload
  },
  data() {
    return {
      rules,
      fileType: ['bmp', 'png', 'jpg', 'jpeg'],
      enterpriseData: {
        "searchValue": null,
        "createBy": "",
        "createTime": null,
        "updateBy": "",
        "updateTime": null,
        "remark": "",
        "params": {},
        "id": "1",
        "userName": "",
        "affiliation": "",
        "password": "",
        "salt": "",
        "mobile": "",
        "name": "",
        "shortName": "",
        "addr": null,
        "bank": null,
        "account": "",
        "taxNo": "",
        "contact": "",
        "contactTel": "",
        "usci": "",
        "address": "",
        "enterpriseNature": "",
        "doe": null,
        "corpn": "",
        "corpnIdcard": "",
        "contactAddress": "",
        "businessScope": "",
        "profile": null,
        "businessLicenseImage": "",
        "businessLicenseGp": "",
        "corpnIdcardImage": null,
        "registrationPhone": "",
        "contactAddr": "",
        "registeredCapital": "",
        "paidinCapital": "",
        "accountLicence": "",
        "ipAddress": ""
      },
      imageUrl: "",
      imageVisible: false,
      imageVisibleTitle: '',
      open: false,

      enterpriseAffiliation: [],
      enterpriseNature: []
    }
  },
  created() {
    this.getDicts('enterprise_affiliation').then(response => {
      this.enterpriseAffiliation = response.data
    })
    this.getDicts('enterprise_nature').then(response => {
      this.enterpriseNature = response.data
    })
  },
  mounted() {
    const that = this
    getEnterprise(this.userId).then(response => {
      that.enterpriseData = response.data

    })
  },
  methods: {
    parseTime,
    getBaseUrl,
    showImage(url, title) {
      this.imageUrl = url;
      this.imageVisible = true;
      this.imageVisibleTitle = title;
    },
    closeImage() {
      this.imageVisible = !this.imageVisible
    },
    handleUpdate() {
      const that = this
      getEnterprise(this.userId).then(response => {
        that.enterpriseData = response.data
        that.open = true

      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.enterpriseData.id != null) {
            updateEnterprise(this.enterpriseData).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
            })
          }
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    getDictValue(key, type) {
      const value = this[type].filter(x => x.dictValue === key);
      if (value != null && value.length > 0) {
        return value[0].dictLabel;
      }
      return ''
    }
  },
  computed:{
    ...mapGetters(['userId'])
  }
}
</script>

<style lang="scss" scoped>
.information {
  .content {
    margin: 0px 30px 44px 30px;
    background: #FFFFFF;
    border-radius: 10px;
    opacity: 1;
    width: 1250px;
    min-height: 860px;
    //display: flex;
    //justify-content: flex-start;
    //align-items: flex-start;
    //flex-wrap: nowrap;
    //flex-direction: column;
    //align-content: center;
    //line-height: 40px;
    padding: 20px;

    .label {
      font-family: MicrosoftYaHei, sans-serif;
      font-size: 14px;
      font-weight: normal;
      line-height: 22px;
      text-align: right;
      letter-spacing: 0em;

      color: #333333;
    }

    .text {
      font-family: Source Han Sans CN;
      font-size: 14px;
      font-weight: normal;
      line-height: 22px;
      letter-spacing: 0em;
      /* 次强调正文标题 */
      color: #666666;
    }
  }
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  display: flex;
}
</style>
