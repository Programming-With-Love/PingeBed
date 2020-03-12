package paster.utlis;

import java.text.DecimalFormat;

public class LittleTools {

    /**
     * 获取文件名的后缀名
     * @param filename 文件名
     * @return 后缀名
     */
    public static String getSuffix(String filename) {
        int i;
        if ((i = filename.lastIndexOf(".")) != -1) {
            String suffix = filename.substring(i);

            return suffix;
        }
        return null;
    }

    /**
     * 判断后缀名是否是图片文件
     * @param suffixName 后缀名
     * @return 是否
     */
    public static boolean isPic(String suffixName) {
        if (suffixName.equals(".jpeg") || suffixName.equals(".jpg") || suffixName.equals(".png") || suffixName.equals(".gif") || suffixName.equals(".svg") || suffixName.equals(".bmp") || suffixName.equals(".ico") || suffixName.equals(".tiff")) {
            return true;
        } else {
            System.out.println("用户上传了非图片文件");
            return false;
        }
    }

    /**
     * 获取项目所在根目录的剩余容量
     * @return 项目所在的根目录剩余容量
     */
    public static String freeSize() {
        return new DecimalFormat("#.00").format(((float) FileUtils.root.getFreeSpace() / 1073741824));
    }
}
