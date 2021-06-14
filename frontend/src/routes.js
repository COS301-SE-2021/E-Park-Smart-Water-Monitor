// core components
import Dashboard from "views/dashboard/Dashboard.js";

// import Icons from "views/admin/Icons.js";
import Login from "views/auth/Login.js";
// import Maps from "views/admin/Maps.js";
import Profile from "views/dashboard/Profile.js";
import Device from "views/dashboard/Device.js";
import Admin from "views/admin/Admin";

// import Register from "views/auth/Register.js";
// import Tables from "views/admin/Tables.js";
// @material-ui/icons components
import AccountCircle from "@material-ui/icons/AccountCircle";
import Dns from "@material-ui/icons/Dns";
import Person from "@material-ui/icons/Person";
import Tv from "@material-ui/icons/Tv";
import VpnKey from "@material-ui/icons/VpnKey";
import SupervisorAccountIcon from '@material-ui/icons/SupervisorAccount';

var routes = [
  {
    path: "/index",
    name: "Dashboard",
    icon: Tv,
    iconColor: "Primary",
    component: Dashboard,
    layout: "/dashboard",
  },
  {
    path: "/index",
    name: "Admin",
    icon: SupervisorAccountIcon,
    iconColor: "Warning",
    component: Admin,
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
    layout: "/dashboard",
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
