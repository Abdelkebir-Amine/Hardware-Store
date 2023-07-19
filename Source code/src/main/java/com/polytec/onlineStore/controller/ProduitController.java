package com.polytec.onlineStore.controller;


import com.polytec.onlineStore.entities.Produit;
import com.polytec.onlineStore.service.IServiceClient;
import com.polytec.onlineStore.service.IServiceCommande;
import com.polytec.onlineStore.service.IServiceProduit;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



@Controller
@AllArgsConstructor
public class ProduitController {

    IServiceProduit serviceProduit;
    IServiceClient serviceClient;
    IServiceCommande serviceCommande;

    // Méthode pour spécifier le format de DATE attendu pour la liaison des données du formulaire.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @GetMapping("/gererProduit")
    public String getproduits (Model model,
                                @RequestParam(name = "motCle",defaultValue = "")String motCle,
                                @RequestParam(name = "page",defaultValue = "1")int page,
                                @RequestParam(name = "size",defaultValue = "4")int size) {
        Page<Produit> listePage = serviceProduit.findByNomContains(motCle, PageRequest.of(page-1, size));
        model.addAttribute("data", listePage.getContent());
        model.addAttribute("pages", new int[listePage.getTotalPages()]);
        model.addAttribute("current", listePage.getNumber());
        model.addAttribute("motCle", motCle);
        return "gererProduit";
    }

    @PostMapping("/saveProduit")
    public String saveProduit (@ModelAttribute @Valid Produit p,
                               BindingResult bindingResult,
                               Model model,
                               @RequestParam("file") MultipartFile mf,
                               HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (bindingResult.hasErrors()) {
            return "ajouterProduit";
        }
        // Save product
        serviceProduit.addProduit(p,mf);
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Produit ajouté avec succès!');");
        out.println("window.location.href='/gererProduit';");
        out.println("</script>");
        out.close();
        return "redirect:/gererProduit";
    }

    @GetMapping("/ajouterProduit")
    public String ajouterProduit (Model model) {
        model.addAttribute("produit", new Produit());
        return "ajouterProduit";
    }

    @GetMapping("/editProduit/{id}")
    public String editProduit (@PathVariable Integer id, Model model) {
        model.addAttribute("produit", serviceProduit.getProduit(id));
        return "modifierProduit";
    }

    @PostMapping("/modifierProduit")
    public String modifierProduit (@ModelAttribute @Valid Produit p,
                               BindingResult bindingResult,
                               Model model,
                               @RequestParam("file") MultipartFile mf,
                               HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (bindingResult.hasErrors()) {
            return "modifierProduit";
        }
        // Save product
        serviceProduit.addProduit(p,mf);
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Produit modifié avec succès!');");
        out.println("window.location.href='/gererProduit';");
        out.println("</script>");
        out.close();
        return "redirect:/gererProduit";
    }

    @GetMapping("/deleteProduit/{id}")
    public String deleteProduit (@PathVariable Integer id) {
        serviceProduit.deleteProduit(id);
        return "redirect:/gererProduit";
    }
}
