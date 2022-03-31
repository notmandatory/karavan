import React from 'react'
import "antd/dist/antd.css";
import {Content} from "antd/lib/layout/layout";
import {Button, Col, Descriptions, Divider, Form, Input, Row, Select, Typography} from "antd";
import TitleHeader from "../../Components/TitleHeader";
import {CustomLayout} from "../../Components/CustomLayout";
import axios from "axios";
import {decode as base64_decode, encode as base64_encode} from 'base-64';
import NavBar from "../../Components/Navbar/Navbar";

const { Option } = Select;
const { Title } = Typography;


const API4 = () => {

    const scrollHeight = Math.max(
        document.body.scrollHeight,
        document.documentElement.scrollHeight,
        document.body.offsetHeight,
        document.documentElement.offsetHeight,
        document.body.clientHeight,
        document.documentElement.clientHeight
    );

    const onFinish = (values) => {

    };

    return (<CustomLayout>
        <NavBar isLoggedIn={true} />
        <Content
            style={{
                padding: "50px 50px",
                marginTop: "30px",
                height: `${scrollHeight}px`,
            }}
        >
            <Row justify="center">
                <Col xs={24}>
                    <TitleHeader
                        title="API Catalog"
                        size={2}
                    />
                </Col>
            </Row>
            <Row justify="center" style={{marginTop: "30px"}}>
                <Col xs={24} sm={18} md={12} lg={10} xl={5}>
                    <Form
                        onFinish={onFinish}
                    >

                        <Title level={3}>API4 Get New Address</Title>

                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{
                                    width: "60%",
                                }}
                            >
                                Test Get New Address
                            </Button>
                        </Form.Item>

                    </Form>
                </Col>
            </Row>
        </Content>
    </CustomLayout>);
};

export default API4;