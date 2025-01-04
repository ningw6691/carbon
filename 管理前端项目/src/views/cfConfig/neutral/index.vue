<template>
  <div class="app-container">
    <el-row :gutter="20" style="margin-top:40px">
      <el-col :span="12" :offset="3">
        <div>
          <el-form ref="form" :model="form" :rules="rules" label-width="180px">
            <el-form-item label="二氧化碳与碳比值:" prop="co2Ratio">
              <el-input v-model="form.co2Ratio" placeholder="请输入" type="number" />
            </el-form-item>
            <!-- <el-form-item label="1KG标准煤释放:" prop="coalCo2">
              <el-input v-model="form.coalCo2" placeholder="请输入" type="number" readonly>
                <template slot="append">KG二氧化碳</template></el-input>
            </el-form-item> -->
            <el-form-item label="1度电释放:" prop="electricCo2">
              <el-input v-model="form.electricCo2" placeholder="请输入" type="number">
                <template slot="append">KG二氧化碳</template>
              </el-input>
            </el-form-item>
            <el-form-item label="1吨水释放:" prop="waterCo2">
              <el-input v-model="form.waterCo2" placeholder="请输入" type="number">
                <template slot="append">KG二氧化碳</template></el-input>
            </el-form-item>
            <el-form-item label="1KG汽油释放:" prop="oilCoal">
              <el-input v-model="form.oilCoal" placeholder="请输入" type="number">
                <template slot="append">KG二氧化碳</template>
              </el-input>
            </el-form-item>
          </el-form>
          <el-row :gutter="20">
            <el-col :span="12" :offset="12">
              <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
              <el-button v-no-more-click style="margin-left:40px" @click="cancel">取 消</el-button>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {
    listNeutral,
    addNeutral,
    updateNeutral,
  } from "@/api/cfConfig/neutral";

  export default {
    name: "Neutral",
    components: {},
    data() {
      return {
        // 总条数
        total: 0,
        // 碳中和计算比例表格数据
        neutralList: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          electricCo2: [{
            required: true,
            message: "数值不能为空",
            trigger: "blur"
          }],
          waterCo2: [{
            required: true,
            message: "数值不能为空",
            trigger: "blur"
          }],
          oilCoal: [{
            required: true,
            message: "数值不能为空",
            trigger: "blur"
          }],
          co2Ratio: [{
            required: true,
            message: "数值不能为空",
            trigger: "blur"
          }]
        }
      };
    },
    created() {
      this.getNeutralConfig();
    },
    methods: {
      /** 查询碳中和计算比例列表 */
      getNeutralConfig() {
        listNeutral(this.queryParams).then(response => {
          this.neutralList = response.rows;
          this.total = response.total;
          // 如果数据库没有数据
          if (this.total == 0) {
            this.reset();
          } else {
            // 取列表第一个
            this.form = this.neutralList[0];
          }
        });
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          coalCo2: 2.5,
          electricCo2: null,
          waterCo2: null,
          oilCoal: null,
          co2Ratio: null,
        };
        this.resetForm("form");
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateNeutral(this.form).then(response => {
                this.msgSuccess("修改成功");
              });
            } else {
              addNeutral(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getNeutralConfig();
              });
            }
          }
        });
      },
      cancel() {
        this.getNeutralConfig();
      }
    }
  };

</script>
