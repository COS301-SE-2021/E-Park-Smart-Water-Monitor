import React, {useEffect, useState} from "react";
import ReactDOM from "react-dom";
import { CSSTransition } from "react-transition-group";
import "../../assets/css/modal.css";
import {PuffLoader} from "react-spinners";
import {css} from "@emotion/react";

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
  opacity: 100%
`;

const Modal = props => {
    const [result, setResult] = useState("")

    const closeOnEscapeKeyDown = e => {
        if ((e.charCode || e.keyCode) === 27) {
            props.onClose();
        }
    };

    useEffect(() => {

        if(props.title)
        {
            let m =  <div className="modal" onClick={ ()=>{props.onClose()} }>
                <div className="modal-content" onClick={e => e.stopPropagation()}>
                    { props.title && <div className="modal-header">

                        <h3 className="modal-title">{props.title}</h3>
                        <button type="button" className="close" data-dismiss="modal" onClick={()=>{props.onClose()}}>&times;</button>

                    </div> }

                    <div className="modal-body">{props.children}</div>
                </div>
            </div>

            setResult(m)
        }else{
            let m =  <div className="modal" >
                <div className="modal-content" onClick={e => e.stopPropagation()}>
                    <div className="modal-body">
                        <PuffLoader css={override} size={150} color={"#123abc"} loading={true} speedMultiplier={1.5} />
                    </div>
                </div>
            </div>

            setResult(m)
        }

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
            { result }
        </CSSTransition>,
        document.getElementById("root")
    );
};

export default Modal;
