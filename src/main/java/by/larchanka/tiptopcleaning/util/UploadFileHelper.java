package by.larchanka.tiptopcleaning.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class UploadFileHelper {
    public Optional<String> uploadFile(HttpServletRequest request){
        String root = request.getServletContext().getRealPath("/");
        String uploadPath = root + File.separator + "img" + File.separator + "items";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        Optional<String> imageNameOptional = null;
        try {
            Part filePart = request.getPart("image");
            imageNameOptional = getFileName(filePart);

            String imageName = null;
            if(imageNameOptional.isPresent()){
                imageName = imageNameOptional.get();
            }

            filePart.write(uploadPath + File.separator + imageName);
        } catch (IOException | ServletException e) {
            return Optional.empty();
        }

        return imageNameOptional;
    }

    private Optional<String> getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                String imageName = content.substring(content.lastIndexOf('=') + 2, content.length() - 1);
                return Optional.of(imageName);
            }
        }
        return Optional.empty();
    }
}
