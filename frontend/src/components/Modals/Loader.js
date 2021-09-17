import React, {useEffect} from "react";
import ReactDOM from "react-dom";
import { CSSTransition } from "react-transition-group";
import "../../assets/css/modal.css";
import {PuffLoader} from "react-spinners";
import {css} from "@emotion/react";

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;



const Loader = props => {

    const closeOnEscapeKeyDown = e => {
        if ((e.charCode || e.keyCode) === 27) {
            props.onClose();
        }
    };

    useEffect(() => {
        document.body.addEventListener("keydown", closeOnEscapeKeyDown);
        return function cleanup() {
            document.body.removeEventListener("keydown", closeOnEscapeKeyDown);
        };
    }, []);

    return ReactDOM.createPortal(
        <CSSTransition
            in={props.show}
            unmountOnExit
            timeout={{ enter: 0, exit: 300 }}
        >
            <div className="modal" >
                <div className="modal-content" style={{background:"none", border:"none"}} >
                    <div className="modal-body">
                        <PuffLoader css={override} size={150} color={"#123abc"} loading={true} speedMultiplier={1.5} />
                    </div>
                </div>
            </div>
        </CSSTransition>,
        document.getElementById("root")
    );
};

export default Loader;
