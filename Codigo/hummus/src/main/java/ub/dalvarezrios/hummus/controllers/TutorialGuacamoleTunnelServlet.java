package ub.dalvarezrios.hummus.controllers;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.GuacamoleSocket;
import org.apache.guacamole.net.GuacamoleTunnel;
import org.apache.guacamole.net.InetGuacamoleSocket;
import org.apache.guacamole.net.SimpleGuacamoleTunnel;
import org.apache.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.apache.guacamole.protocol.GuacamoleConfiguration;
import org.apache.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ub.dalvarezrios.hummus.models.service.IVmService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/tunnel")
public class TutorialGuacamoleTunnelServlet extends GuacamoleHTTPTunnelServlet {

    @Autowired
    private IVmService vmService;
    private String port;

    protected final Log _logger = LogFactory.getLog(this.getClass());

    @Override
    @RequestMapping(path = "tunnel", method = { RequestMethod.POST, RequestMethod.GET })
    protected void handleTunnelRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        super.handleTunnelRequest(request, response);
    }

    @GetMapping("/display")
    public String displayVm(Model model, HttpServletRequest request){
        String port = (String) request.getAttribute("port");
        this.port = port;
        model.addAttribute("titulo", (String) request.getAttribute("machineName"));
        model.addAttribute("port", port);

        return "vm/display";
    }

    @Override
    protected GuacamoleTunnel doConnect(HttpServletRequest request) throws GuacamoleException {
        // Create our configuration
        GuacamoleConfiguration config = new GuacamoleConfiguration();
        config.setProtocol("rdp");
        config.setParameter("hostname", "localhost");
        config.setParameter("port", this.port);
        config.setParameter("password", "potato");
        // Connect to guacd - everything is hard-coded here.
        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                new InetGuacamoleSocket("localhost", 4822),
                config
        );

        // Return a new tunnel which uses the connected socket
        return new SimpleGuacamoleTunnel(socket);

    }

}
