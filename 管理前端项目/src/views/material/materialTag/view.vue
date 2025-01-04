<template>
  <el-dialog :title="title" :visible.sync="open" width="500px" :close-on-click-modal="false" :show-close="false" @open="handleOpen">
    <el-row v-if="materialTagList.length>0" class="tag">
      <el-col :span="24">
        <el-row v-for="field in materialTagList" :key="field.id" class="tag-row">
          <el-col :span="5">{{field.fieldName}}:</el-col>
          <el-col :span="19">{{field.fieldValue}}</el-col>
        </el-row>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button v-no-more-click @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { listMaterialTag } from "@/api/material/materialTag";
export default {
  name: "viewMaterialTag",
  props: {
    open: {
      type: Boolean,
      default: false,
    },
    material: {
      type: Object,
      default: {},
    }
  },
  data() {
    return {
      title: '标签预览',
      // 物料标签模板表格数据
      materialTagList: [],
      // 显示项类型字典
      fieldTypeOptions: [],
      // 查询参数
      queryParams: {
        materialId: null,
        categoryId: null,
        fieldType: null,
        orderByColumn: 'sort',
        isAsc: 'asc',
      },
      form: {},
    }
  },
  created() {
    this.getDicts("tag_field_type").then(response => {
      this.fieldTypeOptions = response.data;
    });
  },
  methods: {
    // 取消按钮
    cancel() {
      this.$emit('onCancel');
    },
    handleOpen() {
      this.queryParams.categoryId = this.material.materialCategoryId;
      this.getFieldList();
    },
    /** 查询物料标签模板信息 */
    getFieldList() {
      this.loading = true;
      listMaterialTag(this.queryParams).then(response => {
        this.materialTagList = [];
        response.rows.forEach(field => {
          let obj = {
            fieldName: null,
            fieldValue: null,
          };
          let opt = this.fieldTypeOptions.filter(dict => dict.dictValue == field.fieldType);
          console.log('opt', opt);
          obj.fieldName = opt[0].dictLabel;
          switch (field.fieldType) {
            case 'name':
              obj.fieldValue = this.material.materialName;
              break;
            case 'batchNo':
              obj.fieldValue = this.material.batchNo;
              break;
            case 'productDate':
              obj.fieldValue = this.material.productDate;
              break;
            case 'manufacturer':
              obj.fieldValue = this.material.manufacturer;
              break;
            case 'specification':
              obj.fieldValue = this.material.materialSpecification;
              break;
            case 'model':
              obj.fieldValue = this.material.materialModel;
              break;
            case 'unit':
              obj.fieldValue = this.material.materialUnit;
            default:
              break;
          }
          let nObj = Object.assign(obj, field);
          this.materialTagList.push(nObj);
        })
      });
    },
  }
}
</script>
<style lang="scss" scoped>
.tag {
  border: 4px solid;
  border-color: black;
}
.tag-row {
  padding-left: 5px;
  padding-top: 20px;
  padding-bottom: 20px;
  border-bottom: 2px solid;
  border-color: black;
}
</style>