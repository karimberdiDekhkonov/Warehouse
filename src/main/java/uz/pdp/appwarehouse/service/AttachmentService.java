package uz.pdp.appwarehouse.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Data
@Service
public class AttachmentService {

    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    public Result uploadPhoto(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        Attachment savedAttachment = attachmentRepository.save(attachment);

        //
        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setBytes(file.getBytes());
        attachmentContent.setAttachment(savedAttachment);
        attachmentContentRepository.save(attachmentContent);
        return new Result("Successfully saved",true,savedAttachment.getId());

    }

    public void downloadPhoto(int id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optional = attachmentRepository.findById(id);
        if (optional.isPresent()){
            Attachment attachment = optional.get();
            Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(id);
            if (byAttachment_id.isPresent()){
                AttachmentContent attachmentContent = byAttachment_id.get();
                response.setHeader("Content-Disposition","attachment; filename=\""+attachment.getName()+"\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(),response.getOutputStream());
            }
        }
    }

    public Result editPhoto(Integer id,MultipartHttpServletRequest request) throws IOException {
        Optional<Attachment> optional = attachmentRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Attachment id is not found !",false);
        }
        Attachment attachment = optional.get();
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file==null){
            return new Result("File is empty !",false);
        }
        attachment.setSize(file.getSize());
        attachment.setName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        Attachment savedAttachment = attachmentRepository.save(attachment);

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachment_Id(id);
        if (!optionalAttachmentContent.isPresent()){
            return new Result("AttachmentContent id is not found!",false);
        }

        AttachmentContent attachmentContent = optionalAttachmentContent.get();
        attachmentContent.setAttachment(attachment);
        attachmentContent.setBytes(file.getBytes());
        attachmentContentRepository.save(attachmentContent);
        return new Result("Successfully edited !",true);
    }

    public Result deletePhoto(Integer id){
        Optional<Attachment> optional = attachmentRepository.findById(id);
        if (!optional.isPresent()){
            return new Result("Attachment id is not found !",false);
        }
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachment_Id(id);
        if (!optionalAttachmentContent.isPresent()){
            return new Result("AttachmentContent id is not found !",false);
        }
        Attachment attachment = optional.get();
        AttachmentContent attachmentContent = optionalAttachmentContent.get();
        attachmentRepository.delete(attachment);
        attachmentContentRepository.delete(attachmentContent);
        return new Result("Successfully deleted !",true);
    }
}
