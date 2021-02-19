<template>

    <div class="app-container">

        <h2 style="text-align: center;">发布新课程</h2>

        <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
        <el-step title="填写课程基本信息"/>
        <el-step title="创建课程大纲"/>
        <el-step title="最终发布"/>
        </el-steps>

        <el-button type="text" @click="openChapterDialog()">添加章节</el-button>

        <ul class="chanpterList">
            <li v-for="chapter in chapterVideoList" :key="chapter.id">
                <p>
                    {{chapter.title}}
                    <span class="acts">
                        <el-button style="" type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                        <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                        <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                    </span>
                </p>
                <ul class="chanpterList videoList">
                    <li v-for="video in chapter.children" :key="video.id">
                        <p>
                            {{video.title}}
                            <span class="acts">
                                <el-button style="" type="text" @click="openEditVideo(video.id)">编辑</el-button>
                                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>

        <div>
            <el-button @click="previous">上一步</el-button>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
        </div>

        <!-- 添加和修改章节表单 -->
        <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
            <el-form :model="chapter" label-width="120px">
                <el-form-item label="章节标题">
                    <el-input v-model="chapter.title"/>
                </el-form-item>
                <el-form-item label="章节排序">
                    <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
            <el-form :model="video" label-width="120px">
                <el-form-item label="课时标题">
                <el-input v-model="video.title"/>
                </el-form-item>
                <el-form-item label="课时排序">
                <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
                </el-form-item>
                <el-form-item label="是否免费">
                <el-radio-group v-model="video.free">
                    <el-radio :label="true">免费</el-radio>
                    <el-radio :label="false">默认</el-radio>
                </el-radio-group>
                </el-form-item>
                <el-form-item label="上传视频">
                <!-- TODO -->
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
                <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'

export default {
    data() {
        return {
            courseId: '',
            chapterVideoList: [],
            saveBtnDisabled: false, // 保存按钮是否禁用
            dialogChapterFormVisible: false,
            chapter:{},
            dialogVideoFormVisible: false,
            video:{}
        }
    },

    created() {
        if(this.$route.params && this.$route.params.id){
            this.courseId = this.$route.params.id
            this.getChapterVideo()
        }
    },

    methods: {
        //修改小节
        updateVideo() {
            video.updateVideo(this.video)
            .then(response =>{
                this.dialogVideoFormVisible=false
                this.$message({
                    type: 'success',
                    message: '修改小节成功'
                })
                this.getChapterVideo()
            })
        },
        //编辑小节弹框
        openEditVideo(id){
            this.dialogVideoFormVisible=true
            video.getVideoInfo(id)
            .then(response =>{
                this.video=response.data.video
            })
        },
        //删除小节
        removeVideo(id){
            this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                video.deleteVideo(id)
                .then(response =>{
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getChapterVideo()
                })
            })
        },
        //添加小节
        addVideo(){
            this.video.courseId=this.courseId
            video.addVideo(this.video)
            .then(response =>{
                this.dialogVideoFormVisible=false
                this.$message({
                    type: 'success',
                    message: '添加小节成功!'
                });
                this.getChapterVideo()
            })
        },
        saveOrUpdateVideo(){
            if(!this.video.id){
                this.addVideo()
            }
            else{
                this.updateVideo()
            }
        },
        //添加小节
        openVideo(chapterId){
            this.video={}
            this.dialogVideoFormVisible=true
            this.video.chapterId=chapterId

        },
        //删除章节
        removeChapter(chapterId){
            this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                chapter.deleteChapter(chapterId)
                .then(response =>{
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getChapterVideo()
                })
            })
        },
        //修改章节
        openEditChapter(chapterId) {
            this.dialogChapterFormVisible=true
            chapter.getChapter(chapterId)
            .then(response =>{
                this.chapter=response.data.chapter
            })
        },
        //弹框
        openChapterDialog() {
            this.dialogChapterFormVisible=true
            this.chapter={}
        },
        //修改
        updateChapter() {
            chapter.updateChapter(this.chapter)
            .then(response =>{
                this.dialogChapterFormVisible=false
                this.$message({
                    type: 'success',
                    message: '修改章节成功'
                })
                this.getChapterVideo()
            })
        },
        //添加
        addChapter() {
            this.chapter.courseId=this.courseId
            chapter.addChapter(this.chapter)
            .then(response =>{
                this.dialogChapterFormVisible=false
                this.$message({
                    type: 'success',
                    message: '添加章节成功'
                })
                this.getChapterVideo()
            })
        },
        //添加章节
        saveOrUpdate() {
            if(!this.chapter.id){
                this.addChapter()
            }
            else{
                this.updateChapter()
            }
        },
        getChapterVideo() {
            chapter.getAllChapterVideo(this.courseId)
            .then(response =>{
                this.chapterVideoList=response.data.allChapterVideo
            })
        },
        previous() {
        this.$router.push({ path: '/course/info/'+this.courseId })
        },

        next() {
        this.$router.push({ path: '/course/publish/'+this.courseId })
        }
    }
}
</script>
<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>