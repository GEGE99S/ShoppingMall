package com.shop.service;

import com.shop.entity.BannerAdImg;
import com.shop.repository.BannerAdImgRepository;
import com.shop.repository.ItemRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerAdService {
    private final BannerAdImgRepository bannerAdImgRepository;
    private final ItemRepository itemRepository;
    private final FileService fileService;
    @Value("${adImgLocation}")
    private String itemImgLocation;


    public List<BannerAdImg> getList() {
        return bannerAdImgRepository.findAll();
    }

    public void updateBanner(MultipartFile file, Long itemId) {
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName;
        String fileUrl;
        BannerAdImg bannerAdImg = new BannerAdImg();
        try {
            if (!StringUtils.isEmpty(originalFilename)) {
                uniqueFileName = fileService.uploadFile(itemImgLocation, originalFilename, file.getBytes());
                fileUrl = "/images/adImg/" + uniqueFileName;
                bannerAdImg.setImgName(uniqueFileName);
                bannerAdImg.setOriImgName(originalFilename);
                bannerAdImg.setImgUrl(fileUrl);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        bannerAdImg.setItem(itemRepository.findById(itemId).orElseThrow(EntityExistsException::new));
        bannerAdImgRepository.save(bannerAdImg);
    }

    public ResponseEntity<String> delete(Long bannerId) {
        BannerAdImg bannerAdImg = bannerAdImgRepository.findById(bannerId).orElseThrow(EntityExistsException::new);
        try {
            fileService.deleteFile(itemImgLocation + "/" + bannerAdImg.getImgName());
            bannerAdImgRepository.delete(bannerAdImg);
            return ResponseEntity.ok().body("삭제 성공");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return ResponseEntity.badRequest().body("실패");
    }
}
