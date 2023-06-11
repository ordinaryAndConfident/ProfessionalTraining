package com.xiaoyu.utils;

import java.io.File;

/**
 * @author YuSir
 * @version 1.0
 * @description: TODO
 * @date 2022/11/4 22:21
 */
public class FileUtils {

    // 删除数据库记录 删除对应磁盘文件
    public static void deleteDiskFile(String diskPath){
        File file = new File(diskPath);
        if (file.isFile()){
            file.delete();
        }
    }
}
