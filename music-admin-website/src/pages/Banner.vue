<template>
  <div class="table">
    <div class="container" style="margin-bottom: 15px;">
      <div class="handle-box">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-upload2"
          @click="dialogBanner = true"
          >上传轮播图</el-button
        >
        <el-button
          type="danger"
          size="mini"
          icon="el-icon-delete"
          @click="deleteSelected"
          >批量删除</el-button
        >
      </div>
    </div>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" prop="id"></el-table-column>
      <el-table-column label="轮播图片" align="center" width="200px">
        <template slot-scope="scope">
          <el-upload
            :action="updateBanner(scope.row.id)"
            :on-success="handleSuccess"
            :before-upload="handleBeforeUpload"
          >
            <div class="singer-avatar">
              <img
                :src="getBaseUrl(scope.row.url)"
                :alt="scope.row.url"
                style="width:100%;"
              />
            </div>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="启用/停用轮播图" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isShow"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="启用"
            inactive-text="停用"
            :active-value="1"
            :inactive-value="0"
            @change="handleIsShow($event, scope.row.id)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="185px" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            icon="el-icon-delete"
            type="danger"
            @click="handleDelete(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <div class="pageHelper">
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <!-- 上传轮播图 -->
    <el-dialog
      title="上传轮播图"
      :visible.sync="dialogBanner"
      width="600px"
      center
    >
      <el-upload
        class="upload-demo"
        drag
        :action="uploadBanner()"
        :on-success="handleUploadSuccess"
        :show-file-list="false"
        :before-upload="handleBeforeUpload"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </el-dialog>
    <!--删除轮播图-->
    <el-dialog
      title="删除轮播图"
      :visible.sync="dialogDel"
      width="300px"
      center
    >
      <div align="center">确定要删除吗?</div>
      <span slot="footer">
        <el-button size="mini" @click="dialogDel = false">取消</el-button>
        <el-button size="mini" @click="handleDel">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getAllBanner, updateBanner, deleteBanner } from "@/api/http";
import { mixin } from "@/mixins/index";

export default {
  mixins: [mixin],
  data() {
    return {
      dialogDel: false,
      dialogBanner: false,
      tableData: [],
      pageSize: 4,
      currentPage: 1,
      idx: -1,
      selected: [], // 选择了哪些
      total: 0
    };
  },
  created() {
    this.selectAll();
  },
  methods: {
    selectAll() {
      this.tableData = [];
      getAllBanner().then(res => {
        if (res.code == 200) {
          this.tableData = res.data;
          this.searchDate = res.data;
          if (res.data == null) {
            this.total = 0;
          } else {
            this.total = this.tableData.length;
          }
        }
      });
    },
    //更新轮播图
    updateBanner(id) {
      return `${this.$store.state.HOST}/banner/upload/banner?id=${id}`;
    },
    uploadBanner() {
      return `${this.$store.state.HOST}/banner/upload/banner?id=3`;
    },
    // 获取当前页
    handleCurrentChange(current) {
      this.currentPage = current;
    },
    handleIsShow($event, id) {
      if ($event == 0) {
        updateBanner({ id: id, isShow: 0 }).then(res => {
          if (res.code == 200) {
            this.$message({
              message: "已停用",
              type: "warning",
              showClose: true
            });
          }
        });
      } else {
        updateBanner({ id: id, isShow: 1 }).then(res => {
          if (res.code == 200) {
            this.$message({
              message: "已启用",
              type: "success",
              showClose: true
            });
          }
        });
      }
    },
    handleDel() {
      deleteBanner({ id: this.idx }).then(res => {
        if (res.code == 200) {
          this.handlePagination();
          this.selectAll(); // 重新查询数据
          this.$message({
            message: "删除成功啦",
            type: "success",
            showClose: true
          });
        } else {
          this.$message({
            message: "删除失败",
            type: "error",
            showClose: true
          });
        }
        this.dialogDel = false;
      });
    },
    handleUploadSuccess(res) {
      if (res.code == 200) {
        this.selectAll(); // 刷新当前页面
        this.$message({
          message: "上传成功",
          type: "success",
          showClose: true
        });
      } else {
        this.$message({
          message: "上传失败",
          type: "error",
          showClose: true
        });
      }
      this.dialogBanner = false;
    },
    handleMultiple() {}
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.handle-box {
  width: 100%;
  margin-bottom: 20px;
}

.singer-avatar {
  width: 100%;
  height: 65px;
  border-radius: 6px;
  margin-bottom: 5px;
  overflow: hidden;
}

.image-box {
  width: 300px;
  height: 300px;
}
.image {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-repeat: no-repeat center;
}

.pageHelper {
  display: flex;
  justify-content: center;
  margin-top: 8px;
}
</style>
