package com.polytec.onlineStore.controller;

import com.polytec.onlineStore.entities.Client;
import com.polytec.onlineStore.entities.Produit;
import com.polytec.onlineStore.service.IServiceClient;
import com.polytec.onlineStore.service.IServiceCommande;
import com.polytec.onlineStore.service.IServiceProduit;
import com.polytec.onlineStore.entities.Commande;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@AllArgsConstructor
public class CommandeController {

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

    @GetMapping("/home")
    public String getCommandes (Model model,
                               @RequestParam(name = "motCle",defaultValue = "")String motCle,
                               @RequestParam(name = "page",defaultValue = "1")int page,
                               @RequestParam(name = "size",defaultValue = "4")int size) {
        Page<Commande> listePage = serviceCommande.findCmdByClientName(motCle, PageRequest.of(page-1, size));
        model.addAttribute("data", listePage.getContent());
        model.addAttribute("pages", new int[listePage.getTotalPages()]);
        model.addAttribute("current", listePage.getNumber());
        model.addAttribute("motCle", motCle);
        return "home";
    }

    @GetMapping("/delete/{id}")
    public String deleteCommande (@PathVariable Integer id) {
        serviceCommande.deleteCommande(id);
        return "redirect:/home";
    }

    @GetMapping("/ajouterCommande")
    public String ajouterCommande (Model model) {
        model.addAttribute("clients", serviceClient.getAllClients());
        model.addAttribute("produits", serviceProduit.getAllProduits());
        model.addAttribute("commande", new Commande());
        model.addAttribute("produit", new Produit());
        return "ajouterCmd";
    }

    @PostMapping("/save")
    public String saveCommande (@ModelAttribute @Valid Commande commande,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("date") Date date,
                                @RequestParam("clientId") Integer clientId,
                                @RequestParam("produitIds") List<Integer> produitIds,
                                HttpServletResponse response) throws ParseException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", serviceClient.getAllClients());
            model.addAttribute("produits", serviceProduit.getAllProduits());
            model.addAttribute("produit", new Produit());
            return "ajouterCmd";
        }

        // récupérer le client correspondant à l'ID sélectionné
        Client client = serviceClient.getClient(clientId);

        // récupérer tous les produits correspondants aux IDs sélectionnés
        List<Produit> produits = serviceProduit.findAllById(produitIds);

        // l'ajouter à la liste de commandes du client
        //Commande commande = new Commande();
        commande.setDate(date);
        commande.setClient(client);
        commande.setProduit(produits);
        client.getCommande().add(commande);

        // sauvegarder la commande et mettre à jour le client correspondant en base de données
        serviceCommande.addCommande(commande);
        serviceClient.addClient(client);

        // Redirection et affichage d'un msg de succès
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Commande effectuée avec succès!');");
        out.println("window.location.href='/home';");
        out.println("</script>");
        out.close();

        return "redirect:/home";
    }

    @GetMapping("/detailsCmd/{id}")
    public String detailsCommande (@PathVariable Integer id, Model model) {
        model.addAttribute("commande", serviceCommande.getCommande(id));
        model.addAttribute("client", serviceClient.getClient(serviceCommande.getCommande(id).getClient().getId()));
        model.addAttribute("produitsSelected",serviceCommande.getCommande(id).getProduit());
        return "detailsCmd";
    }

}
