package com.smartfoxpro.snowflake.controller;

import com.smartfoxpro.snowflake.data.SnowflakeTermsData;
import com.smartfoxpro.snowflake.exception.StorageFileNotFoundException;
import com.smartfoxpro.snowflake.service.SnowflakeService;
import com.smartfoxpro.snowflake.service.StorageFileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Iehor Funtusov, created 08/07/2020 - 10:56 AM
 */

@Controller
@AllArgsConstructor
@RequestMapping("/snowflake")
public class SnowflakeController {

    private final SnowflakeService snowflakeService;
    private final StorageFileService storageFileService;

    @GetMapping("/{id}")
    public String connectToSnowflake(RedirectAttributes redirectAttributes, @PathVariable Long id, Model model) {
        boolean testConnection = snowflakeService.testConnect(id);
        if (testConnection) {
            redirectAttributes.addFlashAttribute("message", "connection successfully");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "connection failed");
        }
        model.addAttribute("id", id);
        model.addAttribute("uploaded", false);
        model.addAttribute("data", new SnowflakeTermsData());
        return "snowflake/snowflake";
    }

    @GetMapping("/{id}/files")
    public String getAllCSVFiles(Model model, @PathVariable Long id) {
        List<String> serveFiles = storageFileService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(SnowflakeController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());
        model.addAttribute("files", serveFiles);
        model.addAttribute("id", id);
        model.addAttribute("uploaded", !serveFiles.isEmpty());
        model.addAttribute("data", new SnowflakeTermsData());
        return "snowflake/snowflake";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageFileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/{id}/files/delete/{filename}")
    public String deleteFile(@PathVariable String filename, @PathVariable Long id) {
        storageFileService.delete(filename);
        return "redirect:/snowflake/" + id + "/files";
    }

    @PostMapping("/{id}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        storageFileService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/snowflake/" + id + "/files";
    }

    @PostMapping("/{id}/runSnowflakeTerms")
    public String runSnowflakeTerms(@PathVariable Long id, @Valid SnowflakeTermsData data, RedirectAttributes redirectAttributes) {
        snowflakeService.runSnowflakeTerms(id, data);
        return "redirect:/snowflake/" + id + "/files";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
