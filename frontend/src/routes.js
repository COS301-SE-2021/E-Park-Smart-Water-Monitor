// core components
import Dashboard from "views/admin/Dashboard.js";
import Login from "views/auth/Login.js";
import Profile from "views/admin/Profile.js";
import Device from "views/admin/Device.js";
import AccountCircle from "@material-ui/icons/AccountCircle";
import Dns from "@material-ui/icons/Dns";
import Person from "@material-ui/icons/Person";
import Tv from "@material-ui/icons/Tv";
import VpnKey from "@material-ui/icons/VpnKey";

var routes = [
  {
    path: "/index",
    name: "Dashboard",
    icon: Tv,
    iconColor: "Primary",
    component: Dashboard,
    layout: "/admin",
  },
  {
    path: "/user-profile",
    name: "Profile",
    icon: Person,
    iconColor: "WarningLight",
    component: Profile,
    layout: "/admin",
  },
  {
    path: "/device",
    name: "Device",
    icon: AccountCircle,
    iconColor: "ErrorLight",
    component: Device,
    layout: "/admin",
  },
  {
    path: "/login",
    name: "Login",
    icon: VpnKey,
    iconColor: "Info",
    component: Login,
    layout: "/auth",
  },
  {
    divider: true,
  },
  {
    title: "Profile",
  },
  // {
  //   href:
  //     "https://www.creative-tim.com/learning-lab/material-ui/overview/argon-dashboard?ref=admui-admin-sidebar",
  //   name: "Getting started",
  //   icon: FlashOn,
  // },
  {
    href:
      "https://www.creative-tim.com/learning-lab/material-ui/alerts/argon-dashboard?ref=admui-admin-sidebar",
    name: "Components",
    icon: Dns,
  },
];
export default routes;
