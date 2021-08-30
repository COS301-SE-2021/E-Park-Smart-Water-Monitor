import Dashboard from "views/dashboard/Dashboard.js";
import Login from "views/auth/Login.js";
import Admin from "views/admin/Admin";
import AccountCircle from "@material-ui/icons/AccountCircle";
import Tv from "@material-ui/icons/Tv";
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
 // {
    // path: "/device",
    // name: "Device",
    // icon: Person,
    // iconColor: "WarningLight",
    // component: Device,
    // layout: "/dashboard",
//  },
  // {
  //   path: "/index",
  //   name: "Login",
  //   icon: VpnKey,
  //   iconColor: "Info",
  //   component: Login,
  //   layout: "/auth",
  // },
  {
    path: "/index",
    name: "Logout",
    icon: AccountCircle,
    iconColor: "ErrorLight",
    component: Login,
    layout: "/auth",
  },
  {
    divider: true,
  },
  {
    title: "Profile",
  },
];
export default routes;
