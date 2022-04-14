package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public Result uploadPhoto(MultipartHttpServletRequest request) throws IOException {
        Result result = attachmentService.uploadPhoto(request);
        return result;
    }

    @GetMapping
    public void downloadPhoto(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.downloadPhoto(id,response);
    }

    @PutMapping("/edit/{id}")
    public Result editPhoto(@PathVariable Integer id,MultipartHttpServletRequest request) throws IOException {
        Result result = attachmentService.editPhoto(id, request);
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Result deletePhoto(@PathVariable Integer id){
        Result result = attachmentService.deletePhoto(id);
        return result;
    }
}
