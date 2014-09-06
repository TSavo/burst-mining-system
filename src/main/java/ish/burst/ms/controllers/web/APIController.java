package ish.burst.ms.controllers.web;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
import ish.burst.ms.objects.api.Plots;
import ish.burst.ms.services.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by ihartney on 9/2/14.
 */
@Controller
@RequestMapping("/api")
public class APIController {

    @Autowired
    PlotService plotService;


    @RequestMapping(value = "/plots", method = RequestMethod.GET)
    public @ResponseBody Plots getPlots() {
        Plots plots = new Plots(plotService.getPlots());
        return plots;
    }

}
