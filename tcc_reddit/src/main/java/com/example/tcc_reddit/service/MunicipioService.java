package com.example.tcc_reddit.service;

import com.example.tcc_reddit.model.Municipio;
import com.example.tcc_reddit.repository.MunicipioRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class MunicipioService {


    private final MunicipioRepository repository;

    public MunicipioService(MunicipioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void importarMunicipiosDoExcel(String caminhoDoArquivo) throws IOException {
        FileInputStream file = new FileInputStream(caminhoDoArquivo);
        //Workbook workbook = new XSSFWorkbook(file);
        Workbook workbook = new HSSFWorkbook(file); //formato antigo do arquivo xls que eu tenho dos municipios

        Sheet sheet = workbook.getSheetAt(0); // Assume que o primeiro sheet contém os dados
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Pula o header do arquivo
            }

            String geoCodigo;
            String nome;
            String longitude;
            String latitude;

            Cell geoCodigoCell = row.getCell(0);
            Cell nomeCell = row.getCell(1);
            Cell longitudeCell = row.getCell(2);
            Cell latitudeCell = row.getCell(3);


            if (geoCodigoCell.getCellType() == CellType.NUMERIC) {
                geoCodigo = String.valueOf((int) geoCodigoCell.getNumericCellValue());
            } else {
                geoCodigo = geoCodigoCell.getStringCellValue();
            }

            if (nomeCell.getCellType() == CellType.NUMERIC) {
                nome = String.valueOf((int) nomeCell.getNumericCellValue());
            } else {
                nome = nomeCell.getStringCellValue();
            }

            if (longitudeCell.getCellType() == CellType.NUMERIC) {
                longitude = String.valueOf(longitudeCell.getNumericCellValue());
            } else {
                longitude = longitudeCell.getStringCellValue();
            }

            if (latitudeCell.getCellType() == CellType.NUMERIC) {
                latitude = String.valueOf(latitudeCell.getNumericCellValue());
            } else {
                latitude = latitudeCell.getStringCellValue();
            }

            Optional<Municipio> existingMunicipio = this.repository.findById(geoCodigo);
            Municipio municipio;
            if (existingMunicipio.isPresent()) {
                municipio = existingMunicipio.get();
                municipio.setNome(nome);
                municipio.setLatitude(latitude);
                municipio.setLongitude(longitude);
            } else {
                municipio = new Municipio();
                municipio.setGeocodigo(geoCodigo);
                municipio.setNome(nome);
                municipio.setLatitude(latitude);
                municipio.setLongitude(longitude);
            }

            this.repository.save(municipio);
        }

        workbook.close();
        file.close();
    }

    public Municipio getRandomMunicipio() {
        try {
            return this.repository.getRandomMunicipio();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar Municipio randomico: " + e.getMessage());
        }
    }
}
