import React from "react";

import { Row, Col, Typography, Layout, Menu } from "antd";
import { MenuOutlined, SettingOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const { Header } = Layout;
const { Title } = Typography;

const NavBar = (props) => {

    const navigate = useNavigate();
    
    const gotoPage = (url) => {
        navigate(url);
    };

    const menu = (
        <Col xl={21} lg={20} md={20} sm={2} xs={4}>
            <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={[props.selected]}
                overflowedIndicator={<MenuOutlined />}
                style={{ justifyContent: "right" }}
                selectedKeys={[props.selected]}
            >
                <Menu.Item id={"API1"} key={"0"} onClick={() => gotoPage("/api1")}>
                    Open Wallet
                </Menu.Item>
                <Menu.Item id={"API2"} key={"1"} onClick={() => gotoPage("/api2")}>
                    Get Wallet Balance
                </Menu.Item>
                <Menu.Item id={"API3"} key={"2"} onClick={() => gotoPage("/api3")}>
                    Close an Open Wallet
                </Menu.Item>
                <Menu.Item id={"API4"} key={"3"} onClick={() => gotoPage("/api4")}>
                    Get New Address
                </Menu.Item>
                <Menu.Item
                    id={"LogoutBtn"}
                    key={"4"}
                    onClick={() => gotoPage("/")}
                    style={{ marginLeft: "auto" }}
                >
                    Logout
                </Menu.Item>
            </Menu>
        </Col>
    );

    return (
        <Header style={{ padding: "0 0" }}>
            <Row
                style={{ width: "100%", height: "100%" }}
                align="middle"
            >
                {props.isLoggedIn && menu}
            </Row>
        </Header>
    );
};

export default NavBar;
