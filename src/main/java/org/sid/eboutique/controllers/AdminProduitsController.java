package org.sid.eboutique.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.sid.eboutique.entities.Categorie;
import org.sid.eboutique.entities.Produit;
import org.sid.eboutique.metier.IAdminProduitsMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/adminProd")
@SessionAttributes("editedProd")
public class AdminProduitsController {

	@Autowired
	private IAdminProduitsMetier metier;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		model.addAttribute("produit", new Produit());
		model.addAttribute("produits", metier.listProduits());
		model.addAttribute("categories", metier.listCategories());
		return "produits";
	}

	@RequestMapping(value = "/saveProd")
	public String saveProd(@Valid Produit p, BindingResult bindingResult,
			Model model, MultipartFile file) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("produits", metier.listProduits());
			model.addAttribute("categories", metier.listCategories());
			return "produits";
		}

		if (!file.isEmpty()) {
			// BufferedImage bi=ImageIO.read(file.getInputStream());
			String path = System.getProperty("java.io.tmpdir");
			p.setPhoto(file.getOriginalFilename());
			Long idP = null;
			if (p.getIdProduit() == null) {
				idP = metier.ajouterProduit(p, p.getCategorie()
						.getIdCategorie());
				file.transferTo(new File(path + "/" + "PROD_" + idP + "_"
						+ file.getOriginalFilename()));
			} else {
				file.transferTo(new File(path + "/" + "PROD_"
						+ p.getIdProduit() + "_" + file.getOriginalFilename()));
				metier.modifierProduit(p);
			}
		} else {
			if (p.getIdProduit() == null) {
				metier.ajouterProduit(p, p.getCategorie().getIdCategorie());
			} else {
				Produit prd = (Produit) model.asMap().get("editedProd");
				p.setPhoto(prd.getPhoto());
				metier.modifierProduit(p);
				model.asMap().remove("editedProd");
			}
		}

		model.addAttribute("produit", new Produit());
		model.addAttribute("produits", metier.listProduits());
		model.addAttribute("categories", metier.listCategories());
		return "produits";
	}

	@RequestMapping(value = "/photoProd", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long idProd) throws IOException {
		Produit p = metier.getProduit(idProd);
		File f = new File(System.getProperty("java.io.tmpdir") + "/PROD_"
				+ idProd + "_" + p.getPhoto());

		return IOUtils.toByteArray(new FileInputStream(f));
	}

	@RequestMapping(value = "/suppProd")
	public String suppProd(Long idProd, Model model) {
		metier.supprimerProduit(idProd);
		model.addAttribute("produit", new Produit());
		model.addAttribute("produits", metier.listProduits());
		model.addAttribute("categories", metier.listCategories());
		return "produits";
	}

	@RequestMapping(value = "/editProd")
	public String editProd(Long idProd, Model model) {
		Produit p = metier.getProduit(idProd);
		model.addAttribute("editedProd", p);
		model.addAttribute("produit", p);
		model.addAttribute("produits", metier.listProduits());
		model.addAttribute("categories", metier.listCategories());
		return "produits";
	}

}
