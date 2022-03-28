package com.rpis82.scalc.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rpis82.scalc.entity.Opening;
import com.rpis82.scalc.entity.OpeningInAStructuralElementFrame;
import com.rpis82.scalc.entity.StructuralElementFrame;

import lombok.Data;

// Эта DTO'шка примет с клиента абсолютно всё необходимое для расчёта каркаса
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StructuralElementFrameDto {
	
	// основные данные по этажу
	private int amountFloor; // кол-во этажей
	private int floorNumber; // номер данного этажа
	private int floorHeight; // высота этажа
	private double perimeterOfExternalWalls; // периметр внешних стен
	private double baseArea; // площадь основания
	private double externalWallThickness; // толщина внешних стен
	private double internalWallLength; // длина внутренних стен
	private double internalWallThickness; // толщина внутренних стен
	
	// НАЧАЛО ДАННЫХ ДЛЯ ОКОН И ДВЕРЕЙ, если они были отмечены как "учесть окна и двери"
	// данные для записи в "openings" (проёмы). туда запишутся тип, длина и высота
//	private String type; // оконный проём, дверной внешний проём или дверной внутренний проём
//	private double height;
//	private double width;
	private List<Opening> openings;
	
	// данные для записи в "openings_in_a_structural_element_frame".
	// в этой записи будет хранится кол-во проёмов нужного типа. плюс нужно будет добавить
	// в эту запись ссылки на экземпляр StructuralElementFrame и Opening
//	private int amount;
	private List<Integer> amounts; // список количеств для каждого из типов проёмов
	// КОНЕЦ ДАННЫХ ДЛЯ ОКОН И ДВЕРЕЙ
	
	// НАЧАЛО ДАННЫХ ДЛЯ ОБШИВКИ ВНЕШНИХ СТЕН
	// В классе ещё есть поля для толщины разных материалов, но эти параметры будут определены в
	// таблице material_characteristics, а таблица material будет хранить тип материала и к какому
	// структурному элементу он относится.
	private String osbExternalWall; // название материала для ОСБ
	private String steamWaterproofingExternalWall; // название материала для парогидроизоляции
	private String windscreenExternalWall; // материал для ветрозащиты
	private String insulationExternalWall; // материал утеплителя
	// КОНЕЦ ДАННЫХ ДЛЯ ОБШИВКИ ВНЕШНИХ СТЕН
	
	// НАЧАЛО ДАННЫХ ДЛЯ ОБШИВКИ ВНУТРЕННИХ СТЕН
	private String osbInternalWall;
	// КОНЕЦ ДАННЫХ ДЛЯ ОБШИВКИ ВНУТРЕННИХ СТЕН
	
	// НАЧАЛО ДАННЫХ ДЛЯ ПЕРЕКРЫТИЯ
	private double overlapThickness;
	private String osbOverlap;
	private String steamWaterproofingOverlap;
	private String windscreenOverlap;
	private String insulationOverlap;
	// КОНЕЦ ДАННЫХ ДЛЯ ПЕРЕКРЫТИЯ
	
	
	
	// Распаковка пришедших данных в экземпляр SEFrame
	public StructuralElementFrame toSEFrame() {
		StructuralElementFrame seFrame = new StructuralElementFrame();
		seFrame.setAmountFloor(amountFloor);
		seFrame.setFloorNumber(floorNumber);
		seFrame.setFloorHeight(floorHeight);
		seFrame.setPerimeterOfExternalWalls(perimeterOfExternalWalls);
		seFrame.setBaseArea(baseArea);
		seFrame.setExternalWallThickness(externalWallThickness);
		seFrame.setInternalWallLength(internalWallLength);
		seFrame.setInternalWallThickness(internalWallThickness);
		
		seFrame.setOsbExternalWall(osbExternalWall);
		seFrame.setSteamWaterproofingExternalWall(steamWaterproofingExternalWall);
		seFrame.setWindscreenExternalWall(windscreenExternalWall);
		seFrame.setInsulationExternalWall(insulationExternalWall);
		
		seFrame.setOsbInternalWall(osbInternalWall);
		
		seFrame.setOverlapThickness(overlapThickness);
		seFrame.setOsbOverlap(osbOverlap);
		seFrame.setSteamWaterproofingOverlap(steamWaterproofingOverlap);
		seFrame.setWindscreenOverlap(windscreenOverlap);
		seFrame.setInsulationOverlap(insulationOverlap);
		
		return seFrame;
	}
	
	// Распаковка пришедших данных в список OpeningInASEFrame
	public List<OpeningInAStructuralElementFrame> toOpeningsInASEFrame() {
		List<OpeningInAStructuralElementFrame> openingsInASEFrame = new ArrayList<>();
		
		for (int i = 0; i < openings.size(); ++i) {
			OpeningInAStructuralElementFrame op;
			op = new OpeningInAStructuralElementFrame();
			op.setOpeningId(openings.get(i));
			op.setAmount(amounts.get(i));
			openingsInASEFrame.add(op);
		}
		
		return openingsInASEFrame;
	}
}

